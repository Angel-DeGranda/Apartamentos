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
import com.blue.apartamentos.services.ClienteService;

@Controller
@RequestMapping("/vista/clientes")
public class ClienteVistaController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new ClienteModel());
        }
        model.addAttribute("tiposCliente", ClienteModel.TipoCliente.values());
        model.addAttribute("estatusCliente", ClienteModel.ClienteStatus.values());
        return "clientes";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute ClienteModel cliente, RedirectAttributes atributos) {
        try {
            clienteService.guardar(cliente);
            atributos.addFlashAttribute("mensaje", "Cliente registrado correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/clientes";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute ClienteModel cliente,
            RedirectAttributes atributos) {
        try {
            clienteService.actualizar(id, cliente);
            atributos.addFlashAttribute("mensaje", "Cliente actualizado correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/vista/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            clienteService.eliminar(id);
            atributos.addFlashAttribute("mensaje", "Cliente eliminado correctamente.");
        } catch (Exception ex) {
            atributos.addFlashAttribute("error",
                    "No se puede eliminar el cliente. Verifique que no tenga propiedades o reservas asociadas.");
        }
        return "redirect:/vista/clientes";
    }
}