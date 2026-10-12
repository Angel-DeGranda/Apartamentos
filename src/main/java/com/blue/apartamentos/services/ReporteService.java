package com.blue.apartamentos.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.dto.ReporteDeudorDTO;
import com.blue.apartamentos.dto.ReporteIngresosDTO;
import com.blue.apartamentos.dto.ReporteOcupacionDTO;
import com.blue.apartamentos.models.PagoModel;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.repositories.IPagoRepository;
import com.blue.apartamentos.repositories.IPropiedadRepository;
import com.blue.apartamentos.repositories.IReservaRepository;

@Service
public class ReporteService {
@Autowired
    private IPropiedadRepository propiedadRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IPagoRepository pagoRepository;

    // Reporte 1: Ocupación por propiedad
    public List<ReporteOcupacionDTO> generarReporteOcupacion(LocalDate fechaInicio, LocalDate fechaFin) {
        List<PropiedadModel> propiedades = propiedadRepository.findAll();
        List<ReservaModel> reservas = reservaRepository.findAll();
        List<ReporteOcupacionDTO> reporte = new ArrayList<>();

        for (PropiedadModel p : propiedades) {
            long conteoReservas = 0;
            long diasReservados = 0;

            for (ReservaModel r : reservas) {
                if (r.getPropiedad().getIdPropiedad().equals(p.getIdPropiedad()) &&
                    (r.getEstado() == ReservaModel.EstadoReserva.CONFIRMADA || r.getEstado() == ReservaModel.EstadoReserva.FINALIZADA)) {

                    // Verificar intersección de fechas
                    if (!r.getFechaInicio().isAfter(fechaFin) && !r.getFechaFin().isBefore(fechaInicio)) {
                        conteoReservas++;
                        long dias = ChronoUnit.DAYS.between(r.getFechaInicio(), r.getFechaFin());
                        diasReservados += (dias > 0) ? dias : 1;
                    }
                }
            }
            reporte.add(new ReporteOcupacionDTO(p.getIdPropiedad(), p.getTitulo(), p.getTipo(), conteoReservas, diasReservados));
        }

        return reporte;
    }

    // Reporte 2: Ingresos liquidados en un período
    public ReporteIngresosDTO generarReporteIngresos(LocalDate fechaInicio, LocalDate fechaFin) {
        List<PagoModel> pagos = pagoRepository.findAll();
        BigDecimal totalIngresos = BigDecimal.ZERO;
        int conteoPagos = 0;

        for (PagoModel p : pagos) {
            if (p.getEstado() == PagoModel.EstadoPago.PAGADO && p.getFechaPago() != null) {
                boolean enRango = !p.getFechaPago().isBefore(fechaInicio) && !p.getFechaPago().isAfter(fechaFin);
                if (enRango) {
                    totalIngresos = totalIngresos.add(p.getMonto());
                    conteoPagos++;
                }
            }
        }

        return new ReporteIngresosDTO(fechaInicio, fechaFin, totalIngresos, conteoPagos);
    }

    // Reporte 3: Lista de morosos / deudores
    public List<ReporteDeudorDTO> generarReporteDeudores() {
        List<PagoModel> pagos = pagoRepository.findAll();
        List<ReporteDeudorDTO> deudores = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (PagoModel p : pagos) {
            boolean esMoroso = (p.getEstado() == PagoModel.EstadoPago.ATRASADO) ||
                               (p.getEstado() == PagoModel.EstadoPago.PENDIENTE && p.getFechaVencimiento().isBefore(hoy));

            if (esMoroso) {
                var arrendatario = p.getReserva().getArrendatario();
                String nombreCompleto = arrendatario.getNombres() + " " + arrendatario.getApellidos();

                deudores.add(new ReporteDeudorDTO(
                    p.getIdPago(),
                    p.getReserva().getIdReserva(),
                    nombreCompleto,
                    arrendatario.getEmail(),
                    arrendatario.getTelefono(),
                    p.getMonto(),
                    p.getFechaVencimiento()
                ));
            }
        }

        return deudores;
    }
}
