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

import com.blue.apartamentos.dto.ReporteDeudorDTO;
import com.blue.apartamentos.dto.ReporteIngresosDTO;
import com.blue.apartamentos.dto.ReporteOcupacionDTO;
import com.blue.apartamentos.services.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    // Reporte de ocupación de propiedades entre fechas (Ej: /api/reportes/ocupacion?fechaInicio=2026-01-01&fechaFin=2026-12-31)
    @GetMapping("/ocupacion")
    public ResponseEntity<List<ReporteOcupacionDTO>> generarReporteOcupacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteService.generarReporteOcupacion(fechaInicio, fechaFin));
    }

    // Reporte de ingresos financieros acumulados (Ej: /api/reportes/ingresos?fechaInicio=2026-01-01&fechaFin=2026-12-31)
    @GetMapping("/ingresos")
    public ResponseEntity<ReporteIngresosDTO> generarReporteIngresos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(reporteService.generarReporteIngresos(fechaInicio, fechaFin));
    }

    // Reporte de deudores
    @GetMapping("/deudores")
    public ResponseEntity<List<ReporteDeudorDTO>> generarReporteDeudores() {
        return ResponseEntity.ok(reporteService.generarReporteDeudores());
    }
}
