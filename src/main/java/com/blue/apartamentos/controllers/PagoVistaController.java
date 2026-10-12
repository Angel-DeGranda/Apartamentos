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

import com.blue.apartamentos.exceptions.BadRequestException;
import com.blue.apartamentos.exceptions.ResourceNotFoundException;
import com.blue.apartamentos.models.PagoModel;
import com.blue.apartamentos.services.PagoService;
import com.blue.apartamentos.services.ReservaService;

@Controller
@RequestMapping("/vista/pagos")
public class PagoVistaController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private ReservaService reservaService;

    // Cargar la vista de gestión de cobros y pagos
    @GetMapping
    public String mostrarPagos(Model model) {
        model.addAttribute("pagos", pagoService.getAll());
        if (!model.containsAttribute("pago")) {
            model.addAttribute("pago", new PagoModel());
        }
        model.addAttribute("reservas", reservaService.getAll());
        model.addAttribute("metodosPago", PagoModel.MetodoPago.values());
        model.addAttribute("estadosPago", PagoModel.EstadoPago.values());
        return "pagos";
    }

    // Registrar un abono o liquidación
    @PostMapping("/guardar")
    public String registrarPago(@ModelAttribute PagoModel pago, RedirectAttributes atributos) {
        try {
            pagoService.registrarPago(pago);
            atributos.addFlashAttribute("mensaje", "Pago registrado correctamente.");
        } catch (BadRequestException | ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/pagos";
    }

    // Modificar el estado de un pago (ej. marcar como PAGADO o ATRASADO)
    @PostMapping("/{id}/actualizar-estado")
    public String actualizarEstado(@PathVariable Long id, @RequestParam PagoModel.EstadoPago nuevoEstado, RedirectAttributes atributos) {
        try {
            pagoService.actualizarEstado(id, nuevoEstado);
            atributos.addFlashAttribute("mensaje", "Estado de pago actualizado.");
        } catch (ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/pagos";
    }
}