package com.blue.apartamentos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavegacionVistaController {

    // Redirigir la raíz del sitio "/" directamente al catálogo de clientes
    @GetMapping("/")
    public String inicio() {
        return "redirect:/vista/clientes";
    }
}