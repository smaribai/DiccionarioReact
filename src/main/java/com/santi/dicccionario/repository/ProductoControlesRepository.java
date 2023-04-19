package com.santi.dicccionario.repository;

import com.santi.dicccionario.domain.ProductoControles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoControles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoControlesRepository extends JpaRepository<ProductoControles, Long> {}
