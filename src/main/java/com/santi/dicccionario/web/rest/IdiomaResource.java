package com.santi.dicccionario.web.rest;

import com.santi.dicccionario.repository.IdiomaRepository;
import com.santi.dicccionario.service.IdiomaService;
import com.santi.dicccionario.service.dto.IdiomaDTO;
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
 * REST controller for managing {@link com.santi.dicccionario.domain.Idioma}.
 */
@RestController
@RequestMapping("/api")
public class IdiomaResource {

    private final Logger log = LoggerFactory.getLogger(IdiomaResource.class);

    private static final String ENTITY_NAME = "idioma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdiomaService idiomaService;

    private final IdiomaRepository idiomaRepository;

    public IdiomaResource(IdiomaService idiomaService, IdiomaRepository idiomaRepository) {
        this.idiomaService = idiomaService;
        this.idiomaRepository = idiomaRepository;
    }

    /**
     * {@code POST  /idiomas} : Create a new idioma.
     *
     * @param idiomaDTO the idiomaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idiomaDTO, or with status {@code 400 (Bad Request)} if the idioma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/idiomas")
    public ResponseEntity<IdiomaDTO> createIdioma(@Valid @RequestBody IdiomaDTO idiomaDTO) throws URISyntaxException {
        log.debug("REST request to save Idioma : {}", idiomaDTO);
        if (idiomaDTO.getCodigo() != null) {
            throw new BadRequestAlertException("A new idioma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdiomaDTO result = idiomaService.save(idiomaDTO);
        return ResponseEntity
            .created(new URI("/api/idiomas/" + result.getCodigo()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCodigo()))
            .body(result);
    }

    /**
     * {@code PUT  /idiomas/:codigo} : Updates an existing idioma.
     *
     * @param codigo the id of the idiomaDTO to save.
     * @param idiomaDTO the idiomaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idiomaDTO,
     * or with status {@code 400 (Bad Request)} if the idiomaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idiomaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/idiomas/{codigo}")
    public ResponseEntity<IdiomaDTO> updateIdioma(
        @PathVariable(value = "codigo", required = false) final String codigo,
        @Valid @RequestBody IdiomaDTO idiomaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Idioma : {}, {}", codigo, idiomaDTO);
        if (idiomaDTO.getCodigo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codigo, idiomaDTO.getCodigo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!idiomaRepository.existsById(codigo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IdiomaDTO result = idiomaService.update(idiomaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idiomaDTO.getCodigo()))
            .body(result);
    }

    /**
     * {@code PATCH  /idiomas/:codigo} : Partial updates given fields of an existing idioma, field will ignore if it is null
     *
     * @param codigo the id of the idiomaDTO to save.
     * @param idiomaDTO the idiomaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idiomaDTO,
     * or with status {@code 400 (Bad Request)} if the idiomaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the idiomaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the idiomaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/idiomas/{codigo}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IdiomaDTO> partialUpdateIdioma(
        @PathVariable(value = "codigo", required = false) final String codigo,
        @NotNull @RequestBody IdiomaDTO idiomaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Idioma partially : {}, {}", codigo, idiomaDTO);
        if (idiomaDTO.getCodigo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codigo, idiomaDTO.getCodigo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!idiomaRepository.existsById(codigo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IdiomaDTO> result = idiomaService.partialUpdate(idiomaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, idiomaDTO.getCodigo())
        );
    }

    /**
     * {@code GET  /idiomas} : get all the idiomas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idiomas in body.
     */
    @GetMapping("/idiomas")
    public ResponseEntity<List<IdiomaDTO>> getAllIdiomas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Idiomas");
        Page<IdiomaDTO> page = idiomaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /idiomas/:id} : get the "id" idioma.
     *
     * @param id the id of the idiomaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idiomaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/idiomas/{id}")
    public ResponseEntity<IdiomaDTO> getIdioma(@PathVariable String id) {
        log.debug("REST request to get Idioma : {}", id);
        Optional<IdiomaDTO> idiomaDTO = idiomaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idiomaDTO);
    }

    /**
     * {@code DELETE  /idiomas/:id} : delete the "id" idioma.
     *
     * @param id the id of the idiomaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/idiomas/{id}")
    public ResponseEntity<Void> deleteIdioma(@PathVariable String id) {
        log.debug("REST request to delete Idioma : {}", id);
        idiomaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
