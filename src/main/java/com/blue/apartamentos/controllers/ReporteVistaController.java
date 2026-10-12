package com.blue.apartamentos.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blue.apartamentos.services.ReporteService;

@Controller
@RequestMapping("/vista/reportes")
public class ReporteVistaController {

    @Autowired
    private ReporteService reporteService;

    // Cargar el tablero unificado de reportes de ocupación, ingresos y deudores
    @GetMapping
    public String mostrarReportes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        // Si no se reciben fechas, se toma por defecto el año en curso
        LocalDate inicio = (fechaInicio != null) ? fechaInicio : LocalDate.now().withDayOfYear(1);
        LocalDate fin = (fechaFin != null) ? fechaFin : LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());

        model.addAttribute("fechaInicio", inicio);
        model.addAttribute("fechaFin", fin);

        // Carga de datos para las 3 secciones de reportes
        model.addAttribute("reporteOcupacion", reporteService.generarReporteOcupacion(inicio, fin));
        model.addAttribute("reporteIngresos", reporteService.generarReporteIngresos(inicio, fin));
        model.addAttribute("reporteDeudores", reporteService.generarReporteDeudores());

        return "reportes";
    }
}