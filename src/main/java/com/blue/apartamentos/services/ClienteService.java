package com.blue.apartamentos.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.models.ClienteModel.TipoCliente;
import com.blue.apartamentos.repositories.IClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private IClienteRepository clienteRepository;

    public List<ClienteModel> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public ClienteModel obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con ID: " + id));
    }

    public List<ClienteModel> obtenerPorTipo(TipoCliente tipo) {
        return clienteRepository.findByTipo(tipo);
    }

    public ClienteModel guardar(ClienteModel cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya se encuentra registrado: " + cliente.getEmail());
        }
        return clienteRepository.save(cliente);
    }

    public ClienteModel actualizar(Long id, ClienteModel clienteActualizado) {
        ClienteModel existente = obtenerPorId(id);

        if (!existente.getEmail().equalsIgnoreCase(clienteActualizado.getEmail()) 
                && clienteRepository.existsByEmail(clienteActualizado.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso por otro cliente.");
        }

        existente.setNombres(clienteActualizado.getNombres());
        existente.setApellidos(clienteActualizado.getApellidos());
        existente.setEmail(clienteActualizado.getEmail());
        existente.setTelefono(clienteActualizado.getTelefono());
        existente.setIne(clienteActualizado.getIne());
        existente.setDireccion(clienteActualizado.getDireccion());
        if (clienteActualizado.getTipo() != null) {
            existente.setTipo(clienteActualizado.getTipo());
        }
        if (clienteActualizado.getEstatus() != null) {
            existente.setEstatus(clienteActualizado.getEstatus());
        }

        return clienteRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NoSuchElementException("No existe el cliente con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}