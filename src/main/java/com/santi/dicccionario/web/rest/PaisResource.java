package com.santi.dicccionario.web.rest;

import com.santi.dicccionario.repository.PaisRepository;
import com.santi.dicccionario.service.PaisService;
import com.santi.dicccionario.service.dto.PaisDTO;
import com.santi.dicccionario.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.santi.dicccionario.domain.Pais}.
 */
@RestController
@RequestMapping("/api")
public class PaisResource {

    private final Logger log = LoggerFactory.getLogger(PaisResource.class);

    private static final String ENTITY_NAME = "pais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaisService paisService;

    private final PaisRepository paisRepository;

    public PaisResource(PaisService paisService, PaisRepository paisRepository) {
        this.paisService = paisService;
        this.paisRepository = paisRepository;
    }

    /**
     * {@code POST  /pais} : Create a new pais.
     *
     * @param paisDTO the paisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paisDTO, or with status {@code 400 (Bad Request)} if the pais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pais")
    public ResponseEntity<PaisDTO> createPais(@Valid @RequestBody PaisDTO paisDTO) throws URISyntaxException {
        log.debug("REST request to save Pais : {}", paisDTO);
        if (paisDTO.getCodigoPais() != null) {
            throw new BadRequestAlertException("A new pais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaisDTO result = paisService.save(paisDTO);
        return ResponseEntity
            .created(new URI("/api/pais/" + result.getCodigoPais()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCodigoPais()))
            .body(result);
    }

    /**
     * {@code PUT  /pais/:codigoPais} : Updates an existing pais.
     *
     * @param codigoPais the id of the paisDTO to save.
     * @param paisDTO the paisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paisDTO,
     * or with status {@code 400 (Bad Request)} if the paisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pais/{codigoPais}")
    public ResponseEntity<PaisDTO> updatePais(
        @PathVariable(value = "codigoPais", required = false) final String codigoPais,
        @Valid @RequestBody PaisDTO paisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pais : {}, {}", codigoPais, paisDTO);
        if (paisDTO.getCodigoPais() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codigoPais, paisDTO.getCodigoPais())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paisRepository.existsById(codigoPais)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaisDTO result = paisService.update(paisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paisDTO.getCodigoPais()))
            .body(result);
    }

    /**
     * {@code PATCH  /pais/:codigoPais} : Partial updates given fields of an existing pais, field will ignore if it is null
     *
     * @param codigoPais the id of the paisDTO to save.
     * @param paisDTO the paisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paisDTO,
     * or with status {@code 400 (Bad Request)} if the paisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pais/{codigoPais}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaisDTO> partialUpdatePais(
        @PathVariable(value = "codigoPais", required = false) final String codigoPais,
        @NotNull @RequestBody PaisDTO paisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pais partially : {}, {}", codigoPais, paisDTO);
        if (paisDTO.getCodigoPais() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codigoPais, paisDTO.getCodigoPais())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paisRepository.existsById(codigoPais)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaisDTO> result = paisService.partialUpdate(paisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paisDTO.getCodigoPais())
        );
    }

    /**
     * {@code GET  /pais} : get all the pais.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pais in body.
     */
    @GetMapping("/pais")
    public ResponseEntity<List<PaisDTO>> getAllPais(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Pais");
        Page<PaisDTO> page = paisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pais/:id} : get the "id" pais.
     *
     * @param id the id of the paisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pais/{id}")
    public ResponseEntity<PaisDTO> getPais(@PathVariable String id) {
        log.debug("REST request to get Pais : {}", id);
        Optional<PaisDTO> paisDTO = paisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paisDTO);
    }

    /**
     * {@code DELETE  /pais/:id} : delete the "id" pais.
     *
     * @param id the id of the paisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pais/{id}")
    public ResponseEntity<Void> deletePais(@PathVariable String id) {
        log.debug("REST request to delete Pais : {}", id);
        paisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
