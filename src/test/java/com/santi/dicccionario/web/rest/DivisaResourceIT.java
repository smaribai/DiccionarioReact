package com.santi.dicccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.dicccionario.IntegrationTest;
import com.santi.dicccionario.domain.Divisa;
import com.santi.dicccionario.repository.DivisaRepository;
import com.santi.dicccionario.service.dto.DivisaDTO;
import com.santi.dicccionario.service.mapper.DivisaMapper;
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
 * Integration tests for the {@link DivisaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DivisaResourceIT {

    private static final String DEFAULT_NOMBRE_DIVISA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DIVISA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/divisas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{codigoDivisa}";

    @Autowired
    private DivisaRepository divisaRepository;

    @Autowired
    private DivisaMapper divisaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisaMockMvc;

    private Divisa divisa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisa createEntity(EntityManager em) {
        Divisa divisa = new Divisa().nombreDivisa(DEFAULT_NOMBRE_DIVISA);
        return divisa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisa createUpdatedEntity(EntityManager em) {
        Divisa divisa = new Divisa().nombreDivisa(UPDATED_NOMBRE_DIVISA);
        return divisa;
    }

    @BeforeEach
    public void initTest() {
        divisa = createEntity(em);
    }

    @Test
    @Transactional
    void createDivisa() throws Exception {
        int databaseSizeBeforeCreate = divisaRepository.findAll().size();
        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);
        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isCreated());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeCreate + 1);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(DEFAULT_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void createDivisaWithExistingId() throws Exception {
        // Create the Divisa with an existing ID
        divisa.setCodigoDivisa("existing_id");
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        int databaseSizeBeforeCreate = divisaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreDivisaIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisaRepository.findAll().size();
        // set the field null
        divisa.setNombreDivisa(null);

        // Create the Divisa, which fails.
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        restDivisaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isBadRequest());

        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDivisas() throws Exception {
        // Initialize the database
        divisa.setCodigoDivisa(UUID.randomUUID().toString());
        divisaRepository.saveAndFlush(divisa);

        // Get all the divisaList
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=codigoDivisa,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].codigoDivisa").value(hasItem(divisa.getCodigoDivisa())))
            .andExpect(jsonPath("$.[*].nombreDivisa").value(hasItem(DEFAULT_NOMBRE_DIVISA)));
    }

    @Test
    @Transactional
    void getDivisa() throws Exception {
        // Initialize the database
        divisa.setCodigoDivisa(UUID.randomUUID().toString());
        divisaRepository.saveAndFlush(divisa);

        // Get the divisa
        restDivisaMockMvc
            .perform(get(ENTITY_API_URL_ID, divisa.getCodigoDivisa()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.codigoDivisa").value(divisa.getCodigoDivisa()))
            .andExpect(jsonPath("$.nombreDivisa").value(DEFAULT_NOMBRE_DIVISA));
    }

    @Test
    @Transactional
    void getNonExistingDivisa() throws Exception {
        // Get the divisa
        restDivisaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDivisa() throws Exception {
        // Initialize the database
        divisa.setCodigoDivisa(UUID.randomUUID().toString());
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();

        // Update the divisa
        Divisa updatedDivisa = divisaRepository.findById(divisa.getCodigoDivisa()).get();
        // Disconnect from session so that the updates on updatedDivisa are not directly saved in db
        em.detach(updatedDivisa);
        updatedDivisa.nombreDivisa(UPDATED_NOMBRE_DIVISA);
        DivisaDTO divisaDTO = divisaMapper.toDto(updatedDivisa);

        restDivisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisaDTO.getCodigoDivisa())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void putNonExistingDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setCodigoDivisa(UUID.randomUUID().toString());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisaDTO.getCodigoDivisa())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setCodigoDivisa(UUID.randomUUID().toString());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setCodigoDivisa(UUID.randomUUID().toString());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(divisaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDivisaWithPatch() throws Exception {
        // Initialize the database
        divisa.setCodigoDivisa(UUID.randomUUID().toString());
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();

        // Update the divisa using partial update
        Divisa partialUpdatedDivisa = new Divisa();
        partialUpdatedDivisa.setCodigoDivisa(divisa.getCodigoDivisa());

        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisa.getCodigoDivisa())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDivisa))
            )
            .andExpect(status().isOk());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(DEFAULT_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void fullUpdateDivisaWithPatch() throws Exception {
        // Initialize the database
        divisa.setCodigoDivisa(UUID.randomUUID().toString());
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();

        // Update the divisa using partial update
        Divisa partialUpdatedDivisa = new Divisa();
        partialUpdatedDivisa.setCodigoDivisa(divisa.getCodigoDivisa());

        partialUpdatedDivisa.nombreDivisa(UPDATED_NOMBRE_DIVISA);

        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisa.getCodigoDivisa())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDivisa))
            )
            .andExpect(status().isOk());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
        Divisa testDivisa = divisaList.get(divisaList.size() - 1);
        assertThat(testDivisa.getNombreDivisa()).isEqualTo(UPDATED_NOMBRE_DIVISA);
    }

    @Test
    @Transactional
    void patchNonExistingDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setCodigoDivisa(UUID.randomUUID().toString());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, divisaDTO.getCodigoDivisa())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setCodigoDivisa(UUID.randomUUID().toString());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDivisa() throws Exception {
        int databaseSizeBeforeUpdate = divisaRepository.findAll().size();
        divisa.setCodigoDivisa(UUID.randomUUID().toString());

        // Create the Divisa
        DivisaDTO divisaDTO = divisaMapper.toDto(divisa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(divisaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Divisa in the database
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDivisa() throws Exception {
        // Initialize the database
        divisa.setCodigoDivisa(UUID.randomUUID().toString());
        divisaRepository.saveAndFlush(divisa);

        int databaseSizeBeforeDelete = divisaRepository.findAll().size();

        // Delete the divisa
        restDivisaMockMvc
            .perform(delete(ENTITY_API_URL_ID, divisa.getCodigoDivisa()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Divisa> divisaList = divisaRepository.findAll();
        assertThat(divisaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
