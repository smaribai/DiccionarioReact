package com.santi.dicccionario.web.rest;

import com.santi.dicccionario.repository.TipoClienteRepository;
import com.santi.dicccionario.service.TipoClienteService;
import com.santi.dicccionario.service.dto.TipoClienteDTO;
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
 * REST controller for managing {@link com.santi.dicccionario.domain.TipoCliente}.
 */
@RestController
@RequestMapping("/api")
public class TipoClienteResource {

    private final Logger log = LoggerFactory.getLogger(TipoClienteResource.class);

    private static final String ENTITY_NAME = "tipoCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoClienteService tipoClienteService;

    private final TipoClienteRepository tipoClienteRepository;

    public TipoClienteResource(TipoClienteService tipoClienteService, TipoClienteRepository tipoClienteRepository) {
        this.tipoClienteService = tipoClienteService;
        this.tipoClienteRepository = tipoClienteRepository;
    }

    /**
     * {@code POST  /tipo-clientes} : Create a new tipoCliente.
     *
     * @param tipoClienteDTO the tipoClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoClienteDTO, or with status {@code 400 (Bad Request)} if the tipoCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-clientes")
    public ResponseEntity<TipoClienteDTO> createTipoCliente(@Valid @RequestBody TipoClienteDTO tipoClienteDTO) throws URISyntaxException {
        log.debug("REST request to save TipoCliente : {}", tipoClienteDTO);
        if (tipoClienteDTO.getCodigo() != null) {
            throw new BadRequestAlertException("A new tipoCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoClienteDTO result = tipoClienteService.save(tipoClienteDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-clientes/" + result.getCodigo()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCodigo()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-clientes/:codigo} : Updates an existing tipoCliente.
     *
     * @param codigo the id of the tipoClienteDTO to save.
     * @param tipoClienteDTO the tipoClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoClienteDTO,
     * or with status {@code 400 (Bad Request)} if the tipoClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-clientes/{codigo}")
    public ResponseEntity<TipoClienteDTO> updateTipoCliente(
        @PathVariable(value = "codigo", required = false) final String codigo,
        @Valid @RequestBody TipoClienteDTO tipoClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoCliente : {}, {}", codigo, tipoClienteDTO);
        if (tipoClienteDTO.getCodigo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codigo, tipoClienteDTO.getCodigo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoClienteRepository.existsById(codigo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoClienteDTO result = tipoClienteService.update(tipoClienteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoClienteDTO.getCodigo()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-clientes/:codigo} : Partial updates given fields of an existing tipoCliente, field will ignore if it is null
     *
     * @param codigo the id of the tipoClienteDTO to save.
     * @param tipoClienteDTO the tipoClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoClienteDTO,
     * or with status {@code 400 (Bad Request)} if the tipoClienteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoClienteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-clientes/{codigo}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoClienteDTO> partialUpdateTipoCliente(
        @PathVariable(value = "codigo", required = false) final String codigo,
        @NotNull @RequestBody TipoClienteDTO tipoClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoCliente partially : {}, {}", codigo, tipoClienteDTO);
        if (tipoClienteDTO.getCodigo() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(codigo, tipoClienteDTO.getCodigo())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoClienteRepository.existsById(codigo)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoClienteDTO> result = tipoClienteService.partialUpdate(tipoClienteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoClienteDTO.getCodigo())
        );
    }

    /**
     * {@code GET  /tipo-clientes} : get all the tipoClientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoClientes in body.
     */
    @GetMapping("/tipo-clientes")
    public ResponseEntity<List<TipoClienteDTO>> getAllTipoClientes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TipoClientes");
        Page<TipoClienteDTO> page = tipoClienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-clientes/:id} : get the "id" tipoCliente.
     *
     * @param id the id of the tipoClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-clientes/{id}")
    public ResponseEntity<TipoClienteDTO> getTipoCliente(@PathVariable String id) {
        log.debug("REST request to get TipoCliente : {}", id);
        Optional<TipoClienteDTO> tipoClienteDTO = tipoClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoClienteDTO);
    }

    /**
     * {@code DELETE  /tipo-clientes/:id} : delete the "id" tipoCliente.
     *
     * @param id the id of the tipoClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-clientes/{id}")
    public ResponseEntity<Void> deleteTipoCliente(@PathVariable String id) {
        log.debug("REST request to delete TipoCliente : {}", id);
        tipoClienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
