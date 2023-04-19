package com.santi.dicccionario.repository;

import com.santi.dicccionario.domain.Control;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Control entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlRepository extends JpaRepository<Control, Long> {}
