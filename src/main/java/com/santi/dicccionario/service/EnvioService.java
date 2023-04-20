package com.santi.dicccionario.service;

import com.santi.dicccionario.service.dto.EnvioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.santi.dicccionario.domain.Envio}.
 */
public interface EnvioService {
    /**
     * Save a envio.
     *
     * @param envioDTO the entity to save.
     * @return the persisted entity.
     */
    EnvioDTO save(EnvioDTO envioDTO);

    /**
     * Updates a envio.
     *
     * @param envioDTO the entity to update.
     * @return the persisted entity.
     */
    EnvioDTO update(EnvioDTO envioDTO);

    /**
     * Partially updates a envio.
     *
     * @param envioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EnvioDTO> partialUpdate(EnvioDTO envioDTO);

    /**
     * Get all the envios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnvioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" envio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnvioDTO> findOne(Long id);

    /**
     * Delete the "id" envio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
