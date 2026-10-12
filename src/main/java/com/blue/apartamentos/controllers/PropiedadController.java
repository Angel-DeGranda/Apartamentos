package com.blue.apartamentos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.services.PropiedadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    // Aquí irán los métodos para manejar las solicitudes HTTP relacionadas con las
    // propiedades
    // Obtener todas las propiedades, obtener por ID, crear, actualizar, eliminar,
    // etc.

    // Recuperar todas la propiedades
    @GetMapping
    public ResponseEntity<List<PropiedadModel>> obtenerTodas() {
        return ResponseEntity.ok(propiedadService.getAllPropiedades());
    }

    // Propiedad por ID
    // ... Similar a ClienteController
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadModel> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(propiedadService.obtenerPorId(id));
    }

    // Crear nueva propiedad
    @PostMapping
    public ResponseEntity<PropiedadModel> guardar(@Valid @RequestBody PropiedadModel propiedad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propiedadService.savePropiedad(propiedad));
    }

    // Actualizar propiedad existente
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadModel> actualizar(@PathVariable Long id,
            @Valid @RequestBody PropiedadModel propiedad) {
        return ResponseEntity.ok(propiedadService.actualizar(id, propiedad));
    }

    // Eliminar una propiedad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        propiedadService.deletePropiedad(id);
        return ResponseEntity.noContent().build();
    }

    // ACTIVIDAD: Crea un metodo para Recuperar propiedades por tipo
    // Recuperar propiedades filtradas por tipo (Ej: /api/propiedades/tipo/PISO o /api/propiedades/tipo/HABITACION)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PropiedadModel>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(propiedadService.obtenerPorTipo(tipo));
    }

    // ACTIVIDAD: Crea un metodo para Recuoperar todas propiedades de un Propietario
    // Consultar las propiedades registradas a nombre de un propietario específico
    @GetMapping("/propietario/{idPropietario}")
    public ResponseEntity<List<PropiedadModel>> obtenerPorPropietario(@PathVariable Long idPropietario) {
        return ResponseEntity.ok(propiedadService.obtenerPorPropietario(idPropietario));
    }
}
