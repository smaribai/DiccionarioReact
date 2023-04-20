package com.santi.dicccionario.repository;

import com.santi.dicccionario.domain.TipoCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, String> {}
