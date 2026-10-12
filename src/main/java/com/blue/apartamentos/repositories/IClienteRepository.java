package com.blue.apartamentos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.apartamentos.models.ClienteModel;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteModel, Long> {
    Optional<ClienteModel> findByEmail(String email);

    Optional<ClienteModel> findByIne(String ine);

    List<ClienteModel> findByTipo(ClienteModel.TipoCliente tipo);

    // Métodos booleanos para evitar registrar correos o INE duplicados
    boolean existsByEmail(String email);

    boolean existsByIne(String ine);
}
