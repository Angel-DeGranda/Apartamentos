package com.blue.apartamentos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.apartamentos.models.PropiedadModel;

@Repository
public interface IPropiedadRepository extends JpaRepository<PropiedadModel, Long> {
    // Obtener todas las propiedades de un propietario específico
    List<PropiedadModel> findByPropietarioId(Long idPropietario);

    // Filtrar por "PISO" o "HABITACION"
    List<PropiedadModel> findByTipoIgnoreCase(String tipo);

    // Filtrar por estado de la propiedad (DISPONIBLE, NO_DISPONIBLE, MANTENIMIENTO)
    List<PropiedadModel> findByEstado(PropiedadModel.EstadoPropiedad estado);

    // Filtrar por ciudad
    List<PropiedadModel> findByCiudadIgnoreCase(String ciudad);
    
}
