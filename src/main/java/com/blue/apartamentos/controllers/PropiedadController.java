package com.blue.apartamentos.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.services.PropiedadService;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping
    public ResponseEntity<List<PropiedadModel>> obtenerTodas() {
        return ResponseEntity.ok(propiedadService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadModel> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(propiedadService.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<PropiedadModel>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(propiedadService.obtenerPorTipo(tipo));
    }

    @GetMapping("/propietario/{idPropietario}")
    public ResponseEntity<List<PropiedadModel>> obtenerPorPropietario(@PathVariable Long idPropietario) {
        return ResponseEntity.ok(propiedadService.obtenerPorPropietario(idPropietario));
    }

    @PostMapping
    public ResponseEntity<PropiedadModel> guardar(@RequestBody PropiedadModel propiedad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propiedadService.guardar(propiedad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropiedadModel> actualizar(@PathVariable Long id, @RequestBody PropiedadModel propiedad) {
        return ResponseEntity.ok(propiedadService.actualizar(id, propiedad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        propiedadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> manejarNoEncontrado(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarSolicitudIncorrecta(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<String> manejarViolacionIntegridad(
            org.springframework.dao.DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("No se puede eliminar el registro porque contiene información vinculada en otros módulos.");
    }
}
