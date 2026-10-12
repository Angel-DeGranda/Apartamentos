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
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.services.ReservaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    // Listar todas las reservas del sistema
    @GetMapping
    public ResponseEntity<List<ReservaModel>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.getAll());
    }

    // Ver detalle de una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservaModel> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    // Consultar el historial de reservas de un arrendatario
    @GetMapping("/arrendatario/{idArrendatario}")
    public ResponseEntity<List<ReservaModel>> obtenerPorArrendatario(@PathVariable Long idArrendatario) {
        return ResponseEntity.ok(reservaService.obtenerPorArrendatario(idArrendatario));
    }

    // Crear una nueva reservación
    @PostMapping
    public ResponseEntity<ReservaModel> guardar(@Valid @RequestBody ReservaModel reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.save(reserva));
    }

    // Cancelar una reserva activa
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaModel> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }
}
