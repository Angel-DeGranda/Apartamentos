package com.blue.apartamentos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.exceptions.BadRequestException;
import com.blue.apartamentos.exceptions.ResourceNotFoundException;
import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.repositories.IClienteRepository;
import com.blue.apartamentos.repositories.IPropiedadRepository;
import com.blue.apartamentos.repositories.IReservaRepository;

@Service
public class ReservaService {
    @Autowired
    private IReservaRepository reservaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IPropiedadRepository propiedadRepository;

    public List<ReservaModel> getAll() {
        return reservaRepository.findAll();
    }

    public ReservaModel obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con el ID: " + id));
    }

    public List<ReservaModel> obtenerPorArrendatario(Long idArrendatario) {
        return reservaRepository.findByArrendatarioId(idArrendatario);
    }

    public ReservaModel save(ReservaModel reserva) {
        // Validar coherencia de fechas
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio()) || reserva.getFechaFin().isEqual(reserva.getFechaInicio())) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        // Validar existencia de arrendatario y propiedad
        ClienteModel arrendatario = clienteRepository.findById(reserva.getArrendatario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("El arrendatario especificado no existe"));

        PropiedadModel propiedad = propiedadRepository.findById(reserva.getPropiedad().getIdPropiedad())
                .orElseThrow(() -> new ResourceNotFoundException("La propiedad especificada no existe"));

        // Validar disponibilidad de la propiedad
        if (propiedad.getEstado() != PropiedadModel.EstadoPropiedad.DISPONIBLE) {
            throw new BadRequestException("La propiedad no se encuentra disponible para reservas");
        }

        // Detección de reservas empalmadas mediante iteración Java
        List<ReservaModel> reservasPropiedad = reservaRepository.findByPropiedadIdPropiedad(propiedad.getIdPropiedad());
        for (ReservaModel r : reservasPropiedad) {
            if (r.getEstado() != ReservaModel.EstadoReserva.CANCELADA) {
                boolean empalma = reserva.getFechaInicio().isBefore(r.getFechaFin()) &&
                                  reserva.getFechaFin().isAfter(r.getFechaInicio());
                if (empalma) {
                    throw new BadRequestException("La propiedad ya cuenta con una reserva activa en ese rango de fechas");
                }
            }
        }

        reserva.setArrendatario(arrendatario);
        reserva.setPropiedad(propiedad);
        reserva.setEstado(ReservaModel.EstadoReserva.PENDIENTE);

        return reservaRepository.save(reserva);
    }

    public ReservaModel cancelarReserva(Long id) {
        ReservaModel reserva = obtenerPorId(id);
        reserva.setEstado(ReservaModel.EstadoReserva.CANCELADA);
        return reservaRepository.save(reserva);
    }
}
