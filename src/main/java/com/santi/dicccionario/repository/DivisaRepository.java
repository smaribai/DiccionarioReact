package com.santi.dicccionario.repository;

import com.santi.dicccionario.domain.Divisa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Divisa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisaRepository extends JpaRepository<Divisa, String> {}
