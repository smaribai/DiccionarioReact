package com.santi.dicccionario.web.rest;

import com.santi.dicccionario.repository.EnvioRepository;
import com.santi.dicccionario.service.EnvioService;
import com.santi.dicccionario.service.dto.EnvioDTO;
import com.santi.dicccionario.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.santi.dicccionario.domain.Envio}.
 */
@RestController
@RequestMapping("/api")
public class EnvioResource {

    private final Logger log = LoggerFactory.getLogger(EnvioResource.class);

    private static final String ENTITY_NAME = "envio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvioService envioService;

    private final EnvioRepository envioRepository;

    public EnvioResource(EnvioService envioService, EnvioRepository envioRepository) {
        this.envioService = envioService;
        this.envioRepository = envioRepository;
    }

    /**
     * {@code POST  /envios} : Create a new envio.
     *
     * @param envioDTO the envioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new envioDTO, or with status {@code 400 (Bad Request)} if the envio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/envios")
    public ResponseEntity<EnvioDTO> createEnvio(@RequestBody EnvioDTO envioDTO) throws URISyntaxException {
        log.debug("REST request to save Envio : {}", envioDTO);
        if (envioDTO.getId() != null) {
            throw new BadRequestAlertException("A new envio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvioDTO result = envioService.save(envioDTO);
        return ResponseEntity
            .created(new URI("/api/envios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /envios/:id} : Updates an existing envio.
     *
     * @param id the id of the envioDTO to save.
     * @param envioDTO the envioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envioDTO,
     * or with status {@code 400 (Bad Request)} if the envioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the envioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/envios/{id}")
    public ResponseEntity<EnvioDTO> updateEnvio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnvioDTO envioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Envio : {}, {}", id, envioDTO);
        if (envioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, envioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!envioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnvioDTO result = envioService.update(envioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, envioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /envios/:id} : Partial updates given fields of an existing envio, field will ignore if it is null
     *
     * @param id the id of the envioDTO to save.
     * @param envioDTO the envioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envioDTO,
     * or with status {@code 400 (Bad Request)} if the envioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the envioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the envioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/envios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnvioDTO> partialUpdateEnvio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EnvioDTO envioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Envio partially : {}, {}", id, envioDTO);
        if (envioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, envioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!envioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnvioDTO> result = envioService.partialUpdate(envioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, envioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /envios} : get all the envios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of envios in body.
     */
    @GetMapping("/envios")
    public ResponseEntity<List<EnvioDTO>> getAllEnvios(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Envios");
        Page<EnvioDTO> page = envioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /envios/:id} : get the "id" envio.
     *
     * @param id the id of the envioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the envioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/envios/{id}")
    public ResponseEntity<EnvioDTO> getEnvio(@PathVariable Long id) {
        log.debug("REST request to get Envio : {}", id);
        Optional<EnvioDTO> envioDTO = envioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(envioDTO);
    }

    /**
     * {@code DELETE  /envios/:id} : delete the "id" envio.
     *
     * @param id the id of the envioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/envios/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        log.debug("REST request to delete Envio : {}", id);
        envioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
