package com.blue.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.exceptions.BadRequestException;
import com.blue.apartamentos.exceptions.ResourceNotFoundException;
import com.blue.apartamentos.models.ClienteModel;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.repositories.IClienteRepository;
import com.blue.apartamentos.repositories.IPropiedadRepository;

@Service
public class PropiedadService {

    @Autowired
    private IPropiedadRepository propiedadRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    public List<PropiedadModel> getAllPropiedades() {
        return propiedadRepository.findAll();
    }

    public Optional<PropiedadModel> getPropiedadById(Long id) {
        return propiedadRepository.findById(id);
    }

    public PropiedadModel savePropiedad(PropiedadModel propiedad) {
        // Verificar que el propietario exista en la base de datos
        ClienteModel propietario = clienteRepository.findById(propiedad.getPropietario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("El propietario especificado no existe"));

        // Validar que el cliente tenga rol de PROPIETARIO
        if (propietario.getTipo() != ClienteModel.TipoCliente.PROPIETARIO) {
            throw new BadRequestException("El cliente asignado no tiene el rol de PROPIETARIO");
        }

        propiedad.setPropietario(propietario);
        return propiedadRepository.save(propiedad);
    }

    // ACTIVIDAD: Crea un metodo Recuperar propiedades por tipo
    public PropiedadModel obtenerPorId(Long id) {
        return propiedadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propiedad no encontrada con el ID: " + id));
    }

    public List<PropiedadModel> obtenerPorPropietario(Long idPropietario) {
        return propiedadRepository.findByPropietarioId(idPropietario);
    }

    // Obtener lista de propiedades filtradas por tipo
    public List<PropiedadModel> obtenerPorTipo(String tipo) {
        return propiedadRepository.findByTipoIgnoreCase(tipo);
    }

    public PropiedadModel actualizar(Long id, PropiedadModel propiedadActualizada) {
        PropiedadModel propiedadExistente = obtenerPorId(id);

        propiedadExistente.setTitulo(propiedadActualizada.getTitulo());
        propiedadExistente.setDescripcion(propiedadActualizada.getDescripcion());
        propiedadExistente.setDireccion(propiedadActualizada.getDireccion());
        propiedadExistente.setCiudad(propiedadActualizada.getCiudad());
        propiedadExistente.setEstadoFederativo(propiedadActualizada.getEstadoFederativo());
        propiedadExistente.setCodigoPostal(propiedadActualizada.getCodigoPostal());
        propiedadExistente.setPais(propiedadActualizada.getPais());
        propiedadExistente.setPrecioNoche(propiedadActualizada.getPrecioNoche());
        propiedadExistente.setCapacidad(propiedadActualizada.getCapacidad());
        propiedadExistente.setNumHabitaciones(propiedadActualizada.getNumHabitaciones());
        propiedadExistente.setNumBanos(propiedadActualizada.getNumBanos());
        propiedadExistente.setMetrosCuadrados(propiedadActualizada.getMetrosCuadrados());
        propiedadExistente.setEstado(propiedadActualizada.getEstado());

        return propiedadRepository.save(propiedadExistente);
    }

    public void deletePropiedad(Long id) {
        PropiedadModel propiedad = obtenerPorId(id);
        propiedadRepository.delete(propiedad);
    }

}
