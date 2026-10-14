package com.blue.apartamentos.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.models.OcupacionReporte;
import com.blue.apartamentos.models.PagoModel;
import com.blue.apartamentos.models.PagoModel.EstadoPago;
import com.blue.apartamentos.models.PagoPendiente;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.models.ReporteIngresos;
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.models.ReservaModel.EstadoReserva;
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

    public List<OcupacionReporte> generarReporteOcupacion(LocalDate fechaInicio, LocalDate fechaFin) {
        List<PropiedadModel> propiedades = propiedadRepository.findAll();
        List<ReservaModel> reservas = reservaRepository.findAll();
        List<OcupacionReporte> reporte = new ArrayList<>();

        for (PropiedadModel p : propiedades) {
            long reservasEnPeriodo = 0;
            long diasReservados = 0;

            for (ReservaModel r : reservas) {
                if (r.getPropiedad().getIdPropiedad().equals(p.getIdPropiedad())
                        && r.getEstado() != EstadoReserva.CANCELADA) {

                    boolean traslape = !r.getFechaFin().isBefore(fechaInicio) && !r.getFechaInicio().isAfter(fechaFin);
                    if (traslape) {
                        reservasEnPeriodo++;
                        LocalDate inicioEfectivo = r.getFechaInicio().isBefore(fechaInicio) ? fechaInicio
                                : r.getFechaInicio();
                        LocalDate finEfectivo = r.getFechaFin().isAfter(fechaFin) ? fechaFin : r.getFechaFin();
                        long dias = ChronoUnit.DAYS.between(inicioEfectivo, finEfectivo);
                        diasReservados += Math.max(dias, 1);
                    }
                }
            }

            OcupacionReporte dto = new OcupacionReporte();
            dto.setIdPropiedad(p.getIdPropiedad());
            dto.setTituloPropiedad(p.getTitulo());
            dto.setTipoPropiedad(p.getTipo());
            dto.setTotalReservas(reservasEnPeriodo);
            dto.setDiasReservados(diasReservados);
            reporte.add(dto);
        }

        return reporte;
    }

    public ReporteIngresos generarReporteIngresos(LocalDate fechaInicio, LocalDate fechaFin) {
        List<PagoModel> pagos = pagoRepository.findAll();
        BigDecimal totalIngresos = BigDecimal.ZERO;
        long cantidadPagos = 0;

        for (PagoModel p : pagos) {
            if (p.getEstado() == EstadoPago.PAGADO && p.getFechaPago() != null) {
                boolean enRango = !p.getFechaPago().isBefore(fechaInicio) && !p.getFechaPago().isAfter(fechaFin);
                if (enRango && p.getMonto() != null) {
                    totalIngresos = totalIngresos.add(p.getMonto());
                    cantidadPagos++;
                }
            }
        }

        ReporteIngresos dto = new ReporteIngresos();
        dto.setFechaInicio(fechaInicio);
        dto.setFechaFin(fechaFin);
        dto.setTotalIngresos(totalIngresos);
        dto.setCantidadPagosRegistrados(cantidadPagos);

        return dto;
    }

    public List<PagoPendiente> generarReporteDeudores() {
        List<PagoModel> pagos = pagoRepository.findAll();
        List<PagoPendiente> deudores = new ArrayList<>();

        for (PagoModel p : pagos) {
            if (p.getEstado() == EstadoPago.ATRASADO || p.getEstado() == EstadoPago.PENDIENTE) {
                PagoPendiente dto = new PagoPendiente();
                dto.setIdPago(p.getIdPago());
                dto.setIdReserva(p.getReserva().getIdReserva());
                dto.setNombreCliente(p.getReserva().getArrendatario().getNombres() + " "
                        + p.getReserva().getArrendatario().getApellidos());
                dto.setEmailCliente(p.getReserva().getArrendatario().getEmail());
                dto.setTelefonoCliente(p.getReserva().getArrendatario().getTelefono());
                dto.setMontoPendiente(p.getMonto());
                dto.setFechaVencimiento(p.getFechaVencimiento());
                deudores.add(dto);
            }
        }

        return deudores;
    }
}
