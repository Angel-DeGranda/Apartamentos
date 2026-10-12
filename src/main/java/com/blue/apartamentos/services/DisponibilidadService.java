package com.blue.apartamentos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.apartamentos.exceptions.BadRequestException;
import com.blue.apartamentos.exceptions.ResourceNotFoundException;
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
        return disponibilidadRepository.findByPropiedadIdPropiedad(idPropiedad);
    }

    public DisponibilidadModel save(DisponibilidadModel disponibilidad) {
        // Validar orden de fechas
        if (disponibilidad.getFechaFin().isBefore(disponibilidad.getFechaInicio())) {
            throw new BadRequestException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Verificar existencia de la propiedad
        PropiedadModel propiedad = propiedadRepository.findById(disponibilidad.getPropiedad().getIdPropiedad())
                .orElseThrow(() -> new ResourceNotFoundException("La propiedad especificada no existe"));

        // Validar empalmes de fechas con bloqueos existentes mediante iteración en Java
        List<DisponibilidadModel> disponibilidadesExistentes = disponibilidadRepository.findByPropiedadIdPropiedad(propiedad.getIdPropiedad());
        for (DisponibilidadModel d : disponibilidadesExistentes) {
            boolean empalma = !disponibilidad.getFechaFin().isBefore(d.getFechaInicio()) &&
                              !disponibilidad.getFechaInicio().isAfter(d.getFechaFin());
            if (empalma) {
                throw new BadRequestException("Ya existe un registro de disponibilidad que se empalma con estas fechas");
            }
        }

        disponibilidad.setPropiedad(propiedad);
        return disponibilidadRepository.save(disponibilidad);
    }

    public void delete(Long id) {
        DisponibilidadModel disp = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de disponibilidad no encontrado"));
        disponibilidadRepository.delete(disp);
    }
}
