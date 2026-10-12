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
import com.blue.apartamentos.models.ReservaModel;
import com.blue.apartamentos.services.ClienteService;
import com.blue.apartamentos.services.PropiedadService;
import com.blue.apartamentos.services.ReservaService;

@Controller
@RequestMapping("/vista/reservas")
public class ReservaVistaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PropiedadService propiedadService;

    // Cargar la vista con todas las reservas registradas
    @GetMapping
    public String mostrarReservas(Model model) {
        model.addAttribute("reservas", reservaService.getAll());
        if (!model.containsAttribute("reserva")) {
            model.addAttribute("reserva", new ReservaModel());
        }
        // Solo enviamos a la vista los clientes que son ARRENDATARIOS
        model.addAttribute("arrendatarios", clienteService.obtenerPorTipo(ClienteModel.TipoCliente.ARRENDATARIO));
        model.addAttribute("propiedades", propiedadService.getAllPropiedades());
        return "reservas";
    }

    // Guardar una nueva reserva de inmueble
    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute ReservaModel reserva, RedirectAttributes atributos) {
        try {
            reservaService.save(reserva);
            atributos.addFlashAttribute("mensaje", "Reserva creada correctamente.");
        } catch (BadRequestException | ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/reservas";
    }

    // Cancelar reserva activa
    @PostMapping("/{id}/cancelar")
    public String cancelarReserva(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            reservaService.cancelarReserva(id);
            atributos.addFlashAttribute("mensaje", "La reserva ha sido cancelada.");
        } catch (ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/reservas";
    }
}