package com.santi.dicccionario.repository;

import com.santi.dicccionario.domain.Envio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Envio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {}
