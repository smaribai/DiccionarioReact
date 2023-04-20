package com.santi.dicccionario.service;

import com.santi.dicccionario.service.dto.IdiomaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.dicccionario.domain.Idioma}.
 */
public interface IdiomaService {
    /**
     * Save a idioma.
     *
     * @param idiomaDTO the entity to save.
     * @return the persisted entity.
     */
    IdiomaDTO save(IdiomaDTO idiomaDTO);

    /**
     * Updates a idioma.
     *
     * @param idiomaDTO the entity to update.
     * @return the persisted entity.
     */
    IdiomaDTO update(IdiomaDTO idiomaDTO);

    /**
     * Partially updates a idioma.
     *
     * @param idiomaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IdiomaDTO> partialUpdate(IdiomaDTO idiomaDTO);

    /**
     * Get all the idiomas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IdiomaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" idioma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdiomaDTO> findOne(String id);

    /**
     * Delete the "id" idioma.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
