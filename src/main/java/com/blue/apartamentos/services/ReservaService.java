package com.blue.apartamentos.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.models.DisponibilidadModel;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.models.ReservaModel.EstadoReserva;
import com.blue.apartamentos.repositories.IClienteRepository;
import com.blue.apartamentos.repositories.IDisponibilidadRepository;
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

    @Autowired
    private IDisponibilidadRepository disponibilidadRepository;

    public List<ReservaModel> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public ReservaModel obtenerPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada con ID: " + id));
    }

    public List<ReservaModel> obtenerPorArrendatario(Long idArrendatario) {
        if (!clienteRepository.existsById(idArrendatario)) {
            throw new NoSuchElementException("El cliente arrendatario no existe.");
        }
        return reservaRepository.findByArrendatarioId(idArrendatario);
    }

    public ReservaModel guardar(ReservaModel reserva) {
        if (reserva.getArrendatario() == null || reserva.getArrendatario().getId() == null) {
            throw new IllegalArgumentException("Debe indicar el cliente arrendatario.");
        }
        if (reserva.getPropiedad() == null || reserva.getPropiedad().getIdPropiedad() == null) {
            throw new IllegalArgumentException("Debe indicar la propiedad a reservar.");
        }
        if (reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
            throw new IllegalArgumentException("Las fechas de la reserva son obligatorias.");
        }
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }

        ClienteModel arrendatario = clienteRepository.findById(reserva.getArrendatario().getId())
                .orElseThrow(() -> new NoSuchElementException("El arrendatario no existe."));

        if (arrendatario.getTipo() != ClienteModel.TipoCliente.ARRENDATARIO) {
            throw new IllegalArgumentException("El cliente seleccionado debe ser de tipo ARRENDATARIO.");
        }

        PropiedadModel propiedad = propiedadRepository.findById(reserva.getPropiedad().getIdPropiedad())
                .orElseThrow(() -> new NoSuchElementException("La propiedad no existe."));

        // Validar si la propiedad está bloqueada en esas fechas
        List<DisponibilidadModel> bloqueos = disponibilidadRepository
                .findByPropiedadIdPropiedad(propiedad.getIdPropiedad());
        for (DisponibilidadModel d : bloqueos) {
            if (!d.isDisponible()) {
                boolean traslape = !reserva.getFechaFin().isBefore(d.getFechaInicio())
                        && !reserva.getFechaInicio().isAfter(d.getFechaFin());
                if (traslape) {
                    throw new IllegalArgumentException(
                            "La propiedad no está disponible en el rango de fechas seleccionado.");
                }
            }
        }

        // Validar si ya existe otra reserva activa
        List<ReservaModel> reservasExistentes = reservaRepository
                .findByPropiedadIdPropiedad(propiedad.getIdPropiedad());
        for (ReservaModel r : reservasExistentes) {
            if (r.getEstado() != EstadoReserva.CANCELADA) {
                boolean traslape = !reserva.getFechaFin().isBefore(r.getFechaInicio())
                        && !reserva.getFechaInicio().isAfter(r.getFechaFin());
                if (traslape) {
                    throw new IllegalArgumentException(
                            "Existe una reserva previa confirmada o pendiente en las fechas solicitadas.");
                }
            }
        }

        reserva.setArrendatario(arrendatario);
        reserva.setPropiedad(propiedad);
        if (reserva.getEstado() == null) {
            reserva.setEstado(EstadoReserva.PENDIENTE);
        }

        return reservaRepository.save(reserva);
    }

    public ReservaModel cancelarReserva(Long id) {
        ReservaModel reserva = obtenerPorId(id);
        reserva.setEstado(EstadoReserva.CANCELADA);
        return reservaRepository.save(reserva);
    }
}
