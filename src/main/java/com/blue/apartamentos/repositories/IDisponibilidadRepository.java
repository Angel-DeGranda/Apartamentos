package com.blue.apartamentos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.apartamentos.models.DisponibilidadModel;

@Repository
public interface IDisponibilidadRepository extends JpaRepository<DisponibilidadModel, Long> {

    List<DisponibilidadModel> findByPropiedadIdPropiedad(Long idPropiedad);
}
