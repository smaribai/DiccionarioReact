package com.santi.dicccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.dicccionario.IntegrationTest;
import com.santi.dicccionario.domain.Control;
import com.santi.dicccionario.repository.ControlRepository;
import com.santi.dicccionario.service.dto.ControlDTO;
import com.santi.dicccionario.service.mapper.ControlMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link ControlResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ControlResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/controls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ControlRepository controlRepository;

    @Autowired
    private ControlMapper controlMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControlMockMvc;

    private Control control;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Control createEntity(EntityManager em) {
        Control control = new Control().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
        return control;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Control createUpdatedEntity(EntityManager em) {
        Control control = new Control().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        return control;
    }

    @BeforeEach
    public void initTest() {
        control = createEntity(em);
    }

    @Test
    @Transactional
    void createControl() throws Exception {
        int databaseSizeBeforeCreate = controlRepository.findAll().size();
        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);
        restControlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controlDTO)))
            .andExpect(status().isCreated());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeCreate + 1);
        Control testControl = controlList.get(controlList.size() - 1);
        assertThat(testControl.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testControl.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createControlWithExistingId() throws Exception {
        // Create the Control with an existing ID
        control.setId(1L);
        ControlDTO controlDTO = controlMapper.toDto(control);

        int databaseSizeBeforeCreate = controlRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllControls() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        // Get all the controlList
        restControlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(control.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getControl() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        // Get the control
        restControlMockMvc
            .perform(get(ENTITY_API_URL_ID, control.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(control.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingControl() throws Exception {
        // Get the control
        restControlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewControl() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        int databaseSizeBeforeUpdate = controlRepository.findAll().size();

        // Update the control
        Control updatedControl = controlRepository.findById(control.getId()).get();
        // Disconnect from session so that the updates on updatedControl are not directly saved in db
        em.detach(updatedControl);
        updatedControl.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        ControlDTO controlDTO = controlMapper.toDto(updatedControl);

        restControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controlDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlDTO))
            )
            .andExpect(status().isOk());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
        Control testControl = controlList.get(controlList.size() - 1);
        assertThat(testControl.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testControl.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();
        control.setId(count.incrementAndGet());

        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controlDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();
        control.setId(count.incrementAndGet());

        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(controlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();
        control.setId(count.incrementAndGet());

        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(controlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControlWithPatch() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        int databaseSizeBeforeUpdate = controlRepository.findAll().size();

        // Update the control using partial update
        Control partialUpdatedControl = new Control();
        partialUpdatedControl.setId(control.getId());

        partialUpdatedControl.nombre(UPDATED_NOMBRE);

        restControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControl))
            )
            .andExpect(status().isOk());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
        Control testControl = controlList.get(controlList.size() - 1);
        assertThat(testControl.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testControl.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateControlWithPatch() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        int databaseSizeBeforeUpdate = controlRepository.findAll().size();

        // Update the control using partial update
        Control partialUpdatedControl = new Control();
        partialUpdatedControl.setId(control.getId());

        partialUpdatedControl.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedControl))
            )
            .andExpect(status().isOk());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
        Control testControl = controlList.get(controlList.size() - 1);
        assertThat(testControl.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testControl.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();
        control.setId(count.incrementAndGet());

        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controlDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();
        control.setId(count.incrementAndGet());

        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(controlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControl() throws Exception {
        int databaseSizeBeforeUpdate = controlRepository.findAll().size();
        control.setId(count.incrementAndGet());

        // Create the Control
        ControlDTO controlDTO = controlMapper.toDto(control);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(controlDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Control in the database
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControl() throws Exception {
        // Initialize the database
        controlRepository.saveAndFlush(control);

        int databaseSizeBeforeDelete = controlRepository.findAll().size();

        // Delete the control
        restControlMockMvc
            .perform(delete(ENTITY_API_URL_ID, control.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Control> controlList = controlRepository.findAll();
        assertThat(controlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
