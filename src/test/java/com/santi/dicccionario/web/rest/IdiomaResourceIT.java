package com.santi.dicccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.dicccionario.IntegrationTest;
import com.santi.dicccionario.domain.Idioma;
import com.santi.dicccionario.repository.IdiomaRepository;
import com.santi.dicccionario.service.dto.IdiomaDTO;
import com.santi.dicccionario.service.mapper.IdiomaMapper;
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
 * Integration tests for the {@link IdiomaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IdiomaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/idiomas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{codigo}";

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private IdiomaMapper idiomaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdiomaMockMvc;

    private Idioma idioma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idioma createEntity(EntityManager em) {
        Idioma idioma = new Idioma().nombre(DEFAULT_NOMBRE);
        return idioma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Idioma createUpdatedEntity(EntityManager em) {
        Idioma idioma = new Idioma().nombre(UPDATED_NOMBRE);
        return idioma;
    }

    @BeforeEach
    public void initTest() {
        idioma = createEntity(em);
    }

    @Test
    @Transactional
    void createIdioma() throws Exception {
        int databaseSizeBeforeCreate = idiomaRepository.findAll().size();
        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);
        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isCreated());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeCreate + 1);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createIdiomaWithExistingId() throws Exception {
        // Create the Idioma with an existing ID
        idioma.setCodigo("existing_id");
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        int databaseSizeBeforeCreate = idiomaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = idiomaRepository.findAll().size();
        // set the field null
        idioma.setNombre(null);

        // Create the Idioma, which fails.
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        restIdiomaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isBadRequest());

        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIdiomas() throws Exception {
        // Initialize the database
        idioma.setCodigo(UUID.randomUUID().toString());
        idiomaRepository.saveAndFlush(idioma);

        // Get all the idiomaList
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codigo,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(idioma.getCodigo())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getIdioma() throws Exception {
        // Initialize the database
        idioma.setCodigo(UUID.randomUUID().toString());
        idiomaRepository.saveAndFlush(idioma);

        // Get the idioma
        restIdiomaMockMvc
            .perform(get(ENTITY_API_URL_ID, idioma.getCodigo()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codigo").value(idioma.getCodigo()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingIdioma() throws Exception {
        // Get the idioma
        restIdiomaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIdioma() throws Exception {
        // Initialize the database
        idioma.setCodigo(UUID.randomUUID().toString());
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();

        // Update the idioma
        Idioma updatedIdioma = idiomaRepository.findById(idioma.getCodigo()).get();
        // Disconnect from session so that the updates on updatedIdioma are not directly saved in db
        em.detach(updatedIdioma);
        updatedIdioma.nombre(UPDATED_NOMBRE);
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(updatedIdioma);

        restIdiomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, idiomaDTO.getCodigo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setCodigo(UUID.randomUUID().toString());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, idiomaDTO.getCodigo())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setCodigo(UUID.randomUUID().toString());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setCodigo(UUID.randomUUID().toString());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(idiomaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdiomaWithPatch() throws Exception {
        // Initialize the database
        idioma.setCodigo(UUID.randomUUID().toString());
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();

        // Update the idioma using partial update
        Idioma partialUpdatedIdioma = new Idioma();
        partialUpdatedIdioma.setCodigo(idioma.getCodigo());

        partialUpdatedIdioma.nombre(UPDATED_NOMBRE);

        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdioma.getCodigo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdioma))
            )
            .andExpect(status().isOk());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateIdiomaWithPatch() throws Exception {
        // Initialize the database
        idioma.setCodigo(UUID.randomUUID().toString());
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();

        // Update the idioma using partial update
        Idioma partialUpdatedIdioma = new Idioma();
        partialUpdatedIdioma.setCodigo(idioma.getCodigo());

        partialUpdatedIdioma.nombre(UPDATED_NOMBRE);

        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdioma.getCodigo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdioma))
            )
            .andExpect(status().isOk());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
        Idioma testIdioma = idiomaList.get(idiomaList.size() - 1);
        assertThat(testIdioma.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setCodigo(UUID.randomUUID().toString());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, idiomaDTO.getCodigo())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setCodigo(UUID.randomUUID().toString());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdioma() throws Exception {
        int databaseSizeBeforeUpdate = idiomaRepository.findAll().size();
        idioma.setCodigo(UUID.randomUUID().toString());

        // Create the Idioma
        IdiomaDTO idiomaDTO = idiomaMapper.toDto(idioma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdiomaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(idiomaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Idioma in the database
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdioma() throws Exception {
        // Initialize the database
        idioma.setCodigo(UUID.randomUUID().toString());
        idiomaRepository.saveAndFlush(idioma);

        int databaseSizeBeforeDelete = idiomaRepository.findAll().size();

        // Delete the idioma
        restIdiomaMockMvc
            .perform(delete(ENTITY_API_URL_ID, idioma.getCodigo()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Idioma> idiomaList = idiomaRepository.findAll();
        assertThat(idiomaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
