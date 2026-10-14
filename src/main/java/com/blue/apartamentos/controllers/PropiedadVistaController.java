package com.blue.apartamentos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.services.ClienteService;
import com.blue.apartamentos.services.PropiedadService;

@Controller
@RequestMapping("/vista/propiedades")
public class PropiedadVistaController {

    @Autowired
    private PropiedadService propiedadService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String mostrarPropiedades(Model model) {
        model.addAttribute("propiedades", propiedadService.obtenerTodas());
        if (!model.containsAttribute("propiedad")) {
            model.addAttribute("propiedad", new PropiedadModel());
        }
        model.addAttribute("propietarios", clienteService.obtenerPorTipo(ClienteModel.TipoCliente.PROPIETARIO));
        model.addAttribute("estadosPropiedad", PropiedadModel.EstadoPropiedad.values());
        return "propiedades";
    }

    @PostMapping("/guardar")
    public String guardarPropiedad(@ModelAttribute PropiedadModel propiedad, RedirectAttributes atributos) {
        try {
            propiedadService.guardar(propiedad);
            atributos.addFlashAttribute("mensaje", "Propiedad registrada correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/propiedades";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizarPropiedad(@PathVariable Long id, @ModelAttribute PropiedadModel propiedad, RedirectAttributes atributos) {
        try {
            propiedadService.actualizar(id, propiedad);
            atributos.addFlashAttribute("mensaje", "Propiedad actualizada correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/propiedades";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarPropiedad(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            propiedadService.eliminar(id);
            atributos.addFlashAttribute("mensaje", "Propiedad eliminada correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", "No se puede eliminar la propiedad. Asegúrese de que no tenga reservas asociadas.");
        }
        return "redirect:/vista/propiedades";
    }
}