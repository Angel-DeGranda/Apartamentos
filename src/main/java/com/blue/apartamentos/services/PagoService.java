package com.blue.apartamentos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.exceptions.ResourceNotFoundException;
import com.blue.apartamentos.models.PagoModel;
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.repositories.IPagoRepository;
import com.blue.apartamentos.repositories.IReservaRepository;

@Service
public class PagoService {
    @Autowired
    private IPagoRepository pagoRepository;

    @Autowired
    private IReservaRepository reservaRepository;

    public List<PagoModel> getAll() {
        return pagoRepository.findAll();
    }

    public List<PagoModel> obtenerPorReserva(Long idReserva) {
        return pagoRepository.findByReservaIdReserva(idReserva);
    }

    public PagoModel registrarPago(PagoModel pago) {
        ReservaModel reserva = reservaRepository.findById(pago.getReserva().getIdReserva())
                .orElseThrow(() -> new ResourceNotFoundException("La reserva vinculada no existe"));

        pago.setReserva(reserva);
        return pagoRepository.save(pago);
    }

    public PagoModel actualizarEstado(Long idPago, PagoModel.EstadoPago nuevoEstado) {
        PagoModel pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));

        pago.setEstado(nuevoEstado);
        return pagoRepository.save(pago);
    }
}
