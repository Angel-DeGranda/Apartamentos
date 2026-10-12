package com.blue.apartamentos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteModel>> getAllClientes() {
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getClienteById(id));
    }

    // Filtrar por PROPIETARIO o ARRENDATARIO
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ClienteModel>> obtenerPorTipo(@PathVariable ClienteModel.TipoCliente tipo) {
        return ResponseEntity.ok(clienteService.obtenerPorTipo(tipo));
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<ClienteModel> guardar(@Valid @RequestBody ClienteModel cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.saveCliente(cliente));
    }

    // Actualizar datos de un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteModel cliente) {
        return ResponseEntity.ok(clienteService.actualizar(id, cliente));
    }

    // Eliminar un cliente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}