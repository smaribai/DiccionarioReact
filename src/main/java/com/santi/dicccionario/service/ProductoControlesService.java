package com.santi.dicccionario.service;

import com.santi.dicccionario.service.dto.ProductoControlesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.dicccionario.domain.ProductoControles}.
 */
public interface ProductoControlesService {
    /**
     * Save a productoControles.
     *
     * @param productoControlesDTO the entity to save.
     * @return the persisted entity.
     */
    ProductoControlesDTO save(ProductoControlesDTO productoControlesDTO);

    /**
     * Updates a productoControles.
     *
     * @param productoControlesDTO the entity to update.
     * @return the persisted entity.
     */
    ProductoControlesDTO update(ProductoControlesDTO productoControlesDTO);

    /**
     * Partially updates a productoControles.
     *
     * @param productoControlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoControlesDTO> partialUpdate(ProductoControlesDTO productoControlesDTO);

    /**
     * Get all the productoControles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoControlesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" productoControles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoControlesDTO> findOne(Long id);

    /**
     * Delete the "id" productoControles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
