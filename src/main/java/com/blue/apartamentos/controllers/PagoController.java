package com.blue.apartamentos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.PagoModel;
import com.blue.apartamentos.services.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    // Consultar todos los pagos registrados
    @GetMapping
    public ResponseEntity<List<PagoModel>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.getAll());
    }

    // Ver el estado de cuenta/pagos asociados a una reserva
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<PagoModel>> obtenerPorReserva(@PathVariable Long idReserva) {
        return ResponseEntity.ok(pagoService.obtenerPorReserva(idReserva));
    }

    // Registrar un abono o liquidación
    @PostMapping
    public ResponseEntity<PagoModel> registrarPago(@Valid @RequestBody PagoModel pago) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrarPago(pago));
    }

    // Cambiar el estado de un pago (PENDIENTE, PAGADO, ATRASADO, CANCELADO)
    @PutMapping("/{id}/estado")
    public ResponseEntity<PagoModel> actualizarEstado(@PathVariable Long id, @RequestParam PagoModel.EstadoPago nuevoEstado) {
        return ResponseEntity.ok(pagoService.actualizarEstado(id, nuevoEstado));
    }
}
