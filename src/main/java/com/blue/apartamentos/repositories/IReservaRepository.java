package com.blue.apartamentos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.apartamentos.models.ReservaModel;

@Repository
public interface IReservaRepository extends JpaRepository<ReservaModel, Long> {

    List<ReservaModel> findByArrendatarioId(Long idArrendatario);

    List<ReservaModel> findByPropiedadIdPropiedad(Long idPropiedad);

    List<ReservaModel> findByEstado(ReservaModel.EstadoReserva estado);
}
