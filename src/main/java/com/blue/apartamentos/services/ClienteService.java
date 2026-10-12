package com.blue.apartamentos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.exceptions.BadRequestException;
import com.blue.apartamentos.exceptions.ResourceNotFoundException;
import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.repositories.IClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private IClienteRepository clienteRepository;

    public List<ClienteModel> getAllClientes() {
        return clienteRepository.findAll();
    }

    public ClienteModel  getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
    }

    public List<ClienteModel> obtenerPorTipo(ClienteModel.TipoCliente tipo) {
        return clienteRepository.findByTipo(tipo);
    }

    public ClienteModel saveCliente(ClienteModel cliente) {
        // Validar correo duplicado
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new BadRequestException("El correo electrónico ya se encuentra registrado");
        }
        // Validar INE duplicado si se proporciona
        if (cliente.getIne() != null && !cliente.getIne().trim().isEmpty() && clienteRepository.existsByIne(cliente.getIne())) {
            throw new BadRequestException("El número de INE ya se encuentra registrado");
        }
        return clienteRepository.save(cliente);
    }

    public ClienteModel actualizar(Long id, ClienteModel clienteActualizado) {
        ClienteModel clienteExistente = getClienteById(id);

        // Si cambia el correo, verificar que no pertenezca a otro usuario
        if (!clienteExistente.getEmail().equalsIgnoreCase(clienteActualizado.getEmail()) &&
            clienteRepository.existsByEmail(clienteActualizado.getEmail())) {
            throw new BadRequestException("El nuevo correo ya está en uso por otro cliente");
        }

        clienteExistente.setNombres(clienteActualizado.getNombres());
        clienteExistente.setApellidos(clienteActualizado.getApellidos());
        clienteExistente.setEmail(clienteActualizado.getEmail());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        clienteExistente.setDireccion(clienteActualizado.getDireccion());
        clienteExistente.setEstatus(clienteActualizado.getEstatus());
        clienteExistente.setTipo(clienteActualizado.getTipo());

        return clienteRepository.save(clienteExistente);
    }

    public void deleteCliente(Long id) {
        ClienteModel cliente = getClienteById(id);
        clienteRepository.delete(cliente);
    }
}