package com.blue.apartamentos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.apartamentos.models.PagoModel;

@Repository
public interface IPagoRepository extends JpaRepository<PagoModel, Long> {

    List<PagoModel> findByReservaIdReserva(Long idReserva);

    List<PagoModel> findByEstado(PagoModel.EstadoPago estado);
}
