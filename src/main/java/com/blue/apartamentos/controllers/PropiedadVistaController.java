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

import com.blue.apartamentos.exceptions.BadRequestException;
import com.blue.apartamentos.exceptions.ResourceNotFoundException;
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

    // Cargar la vista principal de inmuebles (pisos/habitaciones)
    @GetMapping
    public String mostrarPropiedades(Model model) {
        model.addAttribute("propiedades", propiedadService.getAllPropiedades());
        if (!model.containsAttribute("propiedad")) {
            model.addAttribute("propiedad", new PropiedadModel());
        }
        // Solo enviamos a la vista los clientes que son PROPIETARIOS
        model.addAttribute("propietarios", clienteService.obtenerPorTipo(ClienteModel.TipoCliente.PROPIETARIO));
        model.addAttribute("estadosPropiedad", PropiedadModel.EstadoPropiedad.values());
        return "propiedades";
    }

    // Registrar piso o habitación
    @PostMapping("/guardar")
    public String guardarPropiedad(@ModelAttribute PropiedadModel propiedad, RedirectAttributes atributos) {
        try {
            propiedadService.savePropiedad(propiedad);
            atributos.addFlashAttribute("mensaje", "Propiedad registrada correctamente.");
        } catch (BadRequestException | ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/propiedades";
    }

    // Actualizar datos de la propiedad
    @PostMapping("/{id}/actualizar")
    public String actualizarPropiedad(@PathVariable Long id, @ModelAttribute PropiedadModel propiedad, RedirectAttributes atributos) {
        try {
            propiedadService.actualizar(id, propiedad);
            atributos.addFlashAttribute("mensaje", "Propiedad actualizada correctamente.");
        } catch (BadRequestException | ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/propiedades";
    }

    // Eliminar propiedad
    @PostMapping("/{id}/eliminar")
    public String eliminarPropiedad(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            propiedadService.deletePropiedad(id);
            atributos.addFlashAttribute("mensaje", "Propiedad eliminada correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", "No se puede eliminar la propiedad. Asegúrese de que no tenga reservas asociadas.");
        }
        return "redirect:/vista/propiedades";
    }
}