package com.blue.apartamentos.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.OcupacionReporte;
import com.blue.apartamentos.models.PagoPendiente;
import com.blue.apartamentos.models.ReporteIngresos;
import com.blue.apartamentos.services.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/ocupacion")
    public ResponseEntity<List<OcupacionReporte>> obtenerReporteOcupacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteService.generarReporteOcupacion(fechaInicio, fechaFin));
    }

    @GetMapping("/ingresos")
    public ResponseEntity<ReporteIngresos> obtenerReporteIngresos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteService.generarReporteIngresos(fechaInicio, fechaFin));
    }

    @GetMapping("/deudores")
    public ResponseEntity<List<PagoPendiente>> obtenerReporteDeudores() {
        return ResponseEntity.ok(reporteService.generarReporteDeudores());
    }
}
