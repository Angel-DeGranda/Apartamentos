package com.blue.apartamentos.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<PropiedadModel> obtenerTodas() {
        return propiedadRepository.findAll();
    }

    public PropiedadModel obtenerPorId(Long id) {
        return propiedadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Propiedad no encontrada con ID: " + id));
    }

    public List<PropiedadModel> obtenerPorPropietario(Long idPropietario) {
        if (!clienteRepository.existsById(idPropietario)) {
            throw new NoSuchElementException("El propietario especificado no existe.");
        }
        return propiedadRepository.findByPropietarioId(idPropietario);
    }

    public List<PropiedadModel> obtenerPorTipo(String tipo) {
        return propiedadRepository.findByTipoIgnoreCase(tipo);
    }

    public PropiedadModel guardar(PropiedadModel propiedad) {
        if (propiedad.getPropietario() == null || propiedad.getPropietario().getId() == null) {
            throw new IllegalArgumentException("Debe especificar un propietario válido para la propiedad.");
        }

        ClienteModel propietario = clienteRepository.findById(propiedad.getPropietario().getId())
                .orElseThrow(() -> new NoSuchElementException("El propietario no existe."));

        if (propietario.getTipo() != ClienteModel.TipoCliente.PROPIETARIO) {
            throw new IllegalArgumentException("El cliente asignado debe ser de tipo PROPIETARIO.");
        }

        propiedad.setPropietario(propietario);
        return propiedadRepository.save(propiedad);
    }

    public PropiedadModel actualizar(Long id, PropiedadModel propiedadActualizada) {
        PropiedadModel existente = obtenerPorId(id);

        existente.setTitulo(propiedadActualizada.getTitulo());
        existente.setDescripcion(propiedadActualizada.getDescripcion());
        existente.setTipo(propiedadActualizada.getTipo());
        existente.setDireccion(propiedadActualizada.getDireccion());
        existente.setCiudad(propiedadActualizada.getCiudad());
        existente.setEstadoFederativo(propiedadActualizada.getEstadoFederativo());
        existente.setCodigoPostal(propiedadActualizada.getCodigoPostal());
        existente.setPais(propiedadActualizada.getPais());
        existente.setPrecioNoche(propiedadActualizada.getPrecioNoche());
        existente.setCapacidad(propiedadActualizada.getCapacidad());
        existente.setNumHabitaciones(propiedadActualizada.getNumHabitaciones());
        if (propiedadActualizada.getEstado() != null) {
            existente.setEstado(propiedadActualizada.getEstado());
        }

        return propiedadRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!propiedadRepository.existsById(id)) {
            throw new NoSuchElementException("No existe la propiedad con ID: " + id);
        }
        propiedadRepository.deleteById(id);
    }

}
