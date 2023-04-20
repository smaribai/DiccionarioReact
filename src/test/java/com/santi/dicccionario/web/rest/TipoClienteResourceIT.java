package com.santi.dicccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.dicccionario.IntegrationTest;
import com.santi.dicccionario.domain.TipoCliente;
import com.santi.dicccionario.repository.TipoClienteRepository;
import com.santi.dicccionario.service.dto.TipoClienteDTO;
import com.santi.dicccionario.service.mapper.TipoClienteMapper;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TipoClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoClienteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{codigo}";

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @Autowired
    private TipoClienteMapper tipoClienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoClienteMockMvc;

    private TipoCliente tipoCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCliente createEntity(EntityManager em) {
        TipoCliente tipoCliente = new TipoCliente().nombre(DEFAULT_NOMBRE);
        return tipoCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCliente createUpdatedEntity(EntityManager em) {
        TipoCliente tipoCliente = new TipoCliente().nombre(UPDATED_NOMBRE);
        return tipoCliente;
    }

    @BeforeEach
    public void initTest() {
        tipoCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoCliente() throws Exception {
        int databaseSizeBeforeCreate = tipoClienteRepository.findAll().size();
        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);
        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeCreate + 1);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createTipoClienteWithExistingId() throws Exception {
        // Create the TipoCliente with an existing ID
        tipoCliente.setCodigo("existing_id");
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        int databaseSizeBeforeCreate = tipoClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoClienteRepository.findAll().size();
        // set the field null
        tipoCliente.setNombre(null);

        // Create the TipoCliente, which fails.
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        restTipoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoClientes() throws Exception {
        // Initialize the database
        tipoCliente.setCodigo(UUID.randomUUID().toString());
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get all the tipoClienteList
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codigo,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(tipoCliente.getCodigo())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getTipoCliente() throws Exception {
        // Initialize the database
        tipoCliente.setCodigo(UUID.randomUUID().toString());
        tipoClienteRepository.saveAndFlush(tipoCliente);

        // Get the tipoCliente
        restTipoClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoCliente.getCodigo()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codigo").value(tipoCliente.getCodigo()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingTipoCliente() throws Exception {
        // Get the tipoCliente
        restTipoClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoCliente() throws Exception {
        // Initialize the database
        tipoCliente.setCodigo(UUID.randomUUID().toString());
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();

        // Update the tipoCliente
        TipoCliente updatedTipoCliente = tipoClienteRepository.findById(tipoCliente.getCodigo()).get();
        // Disconnect from session so that the updates on updatedTipoCliente are not directly saved in db
        em.detach(updatedTipoCliente);
        updatedTipoCliente.nombre(UPDATED_NOMBRE);
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(updatedTipoCliente);

        restTipoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoClienteDTO.getCodigo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setCodigo(UUID.randomUUID().toString());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoClienteDTO.getCodigo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setCodigo(UUID.randomUUID().toString());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setCodigo(UUID.randomUUID().toString());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoClienteWithPatch() throws Exception {
        // Initialize the database
        tipoCliente.setCodigo(UUID.randomUUID().toString());
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();

        // Update the tipoCliente using partial update
        TipoCliente partialUpdatedTipoCliente = new TipoCliente();
        partialUpdatedTipoCliente.setCodigo(tipoCliente.getCodigo());

        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoCliente.getCodigo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCliente))
            )
            .andExpect(status().isOk());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateTipoClienteWithPatch() throws Exception {
        // Initialize the database
        tipoCliente.setCodigo(UUID.randomUUID().toString());
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();

        // Update the tipoCliente using partial update
        TipoCliente partialUpdatedTipoCliente = new TipoCliente();
        partialUpdatedTipoCliente.setCodigo(tipoCliente.getCodigo());

        partialUpdatedTipoCliente.nombre(UPDATED_NOMBRE);

        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoCliente.getCodigo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCliente))
            )
            .andExpect(status().isOk());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
        TipoCliente testTipoCliente = tipoClienteList.get(tipoClienteList.size() - 1);
        assertThat(testTipoCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setCodigo(UUID.randomUUID().toString());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoClienteDTO.getCodigo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setCodigo(UUID.randomUUID().toString());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoCliente() throws Exception {
        int databaseSizeBeforeUpdate = tipoClienteRepository.findAll().size();
        tipoCliente.setCodigo(UUID.randomUUID().toString());

        // Create the TipoCliente
        TipoClienteDTO tipoClienteDTO = tipoClienteMapper.toDto(tipoCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoCliente in the database
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoCliente() throws Exception {
        // Initialize the database
        tipoCliente.setCodigo(UUID.randomUUID().toString());
        tipoClienteRepository.saveAndFlush(tipoCliente);

        int databaseSizeBeforeDelete = tipoClienteRepository.findAll().size();

        // Delete the tipoCliente
        restTipoClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoCliente.getCodigo()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoCliente> tipoClienteList = tipoClienteRepository.findAll();
        assertThat(tipoClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
