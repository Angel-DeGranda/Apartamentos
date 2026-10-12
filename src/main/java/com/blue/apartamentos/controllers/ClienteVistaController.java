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
import com.blue.apartamentos.services.ClienteService;

@Controller
@RequestMapping("/vista/clientes")
public class ClienteVistaController {

    @Autowired
    private ClienteService clienteService;

    // Cargar la vista principal con el catálogo de clientes y el formulario de registro
    @GetMapping
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clienteService.getAllClientes());
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new ClienteModel());
        }
        model.addAttribute("tiposCliente", ClienteModel.TipoCliente.values());
        model.addAttribute("estatusCliente", ClienteModel.ClienteStatus.values());
        return "clientes";
    }

    // Registrar un cliente nuevo
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute ClienteModel cliente, RedirectAttributes atributos) {
        try {
            clienteService.saveCliente(cliente);
            atributos.addFlashAttribute("mensaje", "Cliente registrado correctamente.");
        } catch (BadRequestException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/clientes";
    }

    // Actualizar datos de un cliente existente
    @PostMapping("/{id}/actualizar")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute ClienteModel cliente, RedirectAttributes atributos) {
        try {
            clienteService.actualizar(id, cliente);
            atributos.addFlashAttribute("mensaje", "Cliente actualizado correctamente.");
        } catch (BadRequestException | ResourceNotFoundException ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/clientes";
    }

    // Eliminar un cliente por ID
    @PostMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            clienteService.deleteCliente(id);
            atributos.addFlashAttribute("mensaje", "Cliente eliminado correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", "No se puede eliminar el cliente. Verifique que no tenga propiedades o reservas asociadas.");
        }
        return "redirect:/vista/clientes";
    }
}