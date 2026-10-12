package com.blue.apartamentos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.DisponibilidadModel;
import com.blue.apartamentos.services.DisponibilidadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {
    @Autowired
    private DisponibilidadService disponibilidadService;

    // Obtener las reglas de disponibilidad o bloqueos de una propiedad
    @GetMapping("/propiedad/{idPropiedad}")
    public ResponseEntity<List<DisponibilidadModel>> obtenerPorPropiedad(@PathVariable Long idPropiedad) {
        return ResponseEntity.ok(disponibilidadService.obtenerPorPropiedad(idPropiedad));
    }

    // Registrar un nuevo período de disponibilidad o bloqueo
    @PostMapping
    public ResponseEntity<DisponibilidadModel> guardar(@Valid @RequestBody DisponibilidadModel disponibilidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidadService.save(disponibilidad));
    }

    // Eliminar un período de disponibilidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        disponibilidadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}