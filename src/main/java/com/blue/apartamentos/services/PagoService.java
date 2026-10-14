package com.blue.apartamentos.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.models.PagoModel;
import com.blue.apartamentos.models.PagoModel.EstadoPago;
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.repositories.IPagoRepository;
import com.blue.apartamentos.repositories.IReservaRepository;

@Service
public class PagoService {
    @Autowired
    private IPagoRepository pagoRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    public List<PagoModel> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public PagoModel obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pago no encontrado con ID: " + id));
    }

    public List<PagoModel> obtenerPorReserva(Long idReserva) {
        if (!reservaRepository.existsById(idReserva)) {
            throw new NoSuchElementException("La reserva indicada no existe.");
        }
        return pagoRepository.findByReservaIdReserva(idReserva);
    }

    public PagoModel registrarPago(PagoModel pago) {
        if (pago.getReserva() == null || pago.getReserva().getIdReserva() == null) {
            throw new IllegalArgumentException("Debe vincular el pago a una reserva válida.");
        }

        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }

        ReservaModel reserva = reservaRepository.findById(pago.getReserva().getIdReserva())
                .orElseThrow(() -> new NoSuchElementException("La reserva vinculada no existe."));

        pago.setReserva(reserva);
        if (pago.getEstado() == null) {
            pago.setEstado(EstadoPago.PENDIENTE);
        }

        return pagoRepository.save(pago);
    }

    public PagoModel actualizarEstado(Long id, EstadoPago nuevoEstado) {
        PagoModel pago = obtenerPorId(id);
        pago.setEstado(nuevoEstado);
        return pagoRepository.save(pago);
    }
}
