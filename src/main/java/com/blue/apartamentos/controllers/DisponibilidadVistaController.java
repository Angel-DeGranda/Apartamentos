package com.blue.apartamentos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blue.apartamentos.models.DisponibilidadModel;
import com.blue.apartamentos.services.DisponibilidadService;
import com.blue.apartamentos.services.PropiedadService;

@Controller
@RequestMapping("/vista/disponibilidades")
public class DisponibilidadVistaController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping
    public String mostrarDisponibilidades(@RequestParam(required = false) Long idPropiedad, Model model) {
        var propiedades = propiedadService.obtenerTodas();
        model.addAttribute("propiedades", propiedades);

        Long idSeleccionado = idPropiedad;
        if (idSeleccionado == null && !propiedades.isEmpty()) {
            idSeleccionado = propiedades.get(0).getIdPropiedad();
        }

        if (idSeleccionado != null) {
            model.addAttribute("disponibilidades", disponibilidadService.obtenerPorPropiedad(idSeleccionado));
            model.addAttribute("idPropiedadSeleccionada", idSeleccionado);
        } else {
            model.addAttribute("disponibilidades", java.util.Collections.emptyList());
        }

        if (!model.containsAttribute("disponibilidad")) {
            model.addAttribute("disponibilidad", new DisponibilidadModel());
        }

        return "disponibilidades";
    }

    @PostMapping("/guardar")
    public String guardarDisponibilidad(@ModelAttribute DisponibilidadModel disponibilidad, RedirectAttributes atributos) {
        try {
            disponibilidadService.guardar(disponibilidad);
            atributos.addFlashAttribute("mensaje", "Regla de disponibilidad registrada.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        Long idProp = (disponibilidad.getPropiedad() != null) ? disponibilidad.getPropiedad().getIdPropiedad() : null;
        return "redirect:/vista/disponibilidades" + (idProp != null ? "?idPropiedad=" + idProp : "");
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarDisponibilidad(@PathVariable Long id, @RequestParam(required = false) Long idPropiedad, RedirectAttributes atributos) {
        try {
            disponibilidadService.eliminar(id);
            atributos.addFlashAttribute("mensaje", "Registro de disponibilidad eliminado.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/disponibilidades" + (idPropiedad != null ? "?idPropiedad=" + idPropiedad : "");
    }
}