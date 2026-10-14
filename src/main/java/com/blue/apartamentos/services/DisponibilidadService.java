package com.blue.apartamentos.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.models.DisponibilidadModel;
import com.blue.apartamentos.models.PropiedadModel;
import com.blue.apartamentos.repositories.IDisponibilidadRepository;
import com.blue.apartamentos.repositories.IPropiedadRepository;

@Service
public class DisponibilidadService {
    @Autowired
    private IDisponibilidadRepository disponibilidadRepository;

    @Autowired
    private IPropiedadRepository propiedadRepository;

    public List<DisponibilidadModel> obtenerPorPropiedad(Long idPropiedad) {
        if (!propiedadRepository.existsById(idPropiedad)) {
            throw new NoSuchElementException("Propiedad no encontrada con ID: " + idPropiedad);
        }
        return disponibilidadRepository.findByPropiedadIdPropiedad(idPropiedad);
    }

    public DisponibilidadModel obtenerPorId(Long id) {
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Registro de disponibilidad no encontrado con ID: " + id));
    }

    public DisponibilidadModel guardar(DisponibilidadModel disponibilidad) {
        if (disponibilidad.getPropiedad() == null || disponibilidad.getPropiedad().getIdPropiedad() == null) {
            throw new IllegalArgumentException("Debe especificar la propiedad correspondiente.");
        }

        if (disponibilidad.getFechaInicio() == null || disponibilidad.getFechaFin() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias.");
        }

        if (disponibilidad.getFechaFin().isBefore(disponibilidad.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha de inicio.");
        }

        PropiedadModel propiedad = propiedadRepository.findById(disponibilidad.getPropiedad().getIdPropiedad())
                .orElseThrow(() -> new NoSuchElementException("La propiedad asociada no existe."));

        disponibilidad.setPropiedad(propiedad);
        return disponibilidadRepository.save(disponibilidad);
    }

    public void eliminar(Long id) {
        if (!disponibilidadRepository.existsById(id)) {
            throw new NoSuchElementException("Registro de disponibilidad no encontrado con ID: " + id);
        }
        disponibilidadRepository.deleteById(id);
    }
}
