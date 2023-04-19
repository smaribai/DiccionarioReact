package com.santi.dicccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.dicccionario.IntegrationTest;
import com.santi.dicccionario.domain.ProductoControles;
import com.santi.dicccionario.repository.ProductoControlesRepository;
import com.santi.dicccionario.service.dto.ProductoControlesDTO;
import com.santi.dicccionario.service.mapper.ProductoControlesMapper;
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
 * Integration tests for the {@link ProductoControlesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoControlesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/producto-controles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoControlesRepository productoControlesRepository;

    @Autowired
    private ProductoControlesMapper productoControlesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoControlesMockMvc;

    private ProductoControles productoControles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoControles createEntity(EntityManager em) {
        ProductoControles productoControles = new ProductoControles().descripcion(DEFAULT_DESCRIPCION);
        return productoControles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoControles createUpdatedEntity(EntityManager em) {
        ProductoControles productoControles = new ProductoControles().descripcion(UPDATED_DESCRIPCION);
        return productoControles;
    }

    @BeforeEach
    public void initTest() {
        productoControles = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoControles() throws Exception {
        int databaseSizeBeforeCreate = productoControlesRepository.findAll().size();
        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);
        restProductoControlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createProductoControlesWithExistingId() throws Exception {
        // Create the ProductoControles with an existing ID
        productoControles.setId(1L);
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        int databaseSizeBeforeCreate = productoControlesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoControlesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get all the productoControlesList
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoControles.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        // Get the productoControles
        restProductoControlesMockMvc
            .perform(get(ENTITY_API_URL_ID, productoControles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoControles.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingProductoControles() throws Exception {
        // Get the productoControles
        restProductoControlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();

        // Update the productoControles
        ProductoControles updatedProductoControles = productoControlesRepository.findById(productoControles.getId()).get();
        // Disconnect from session so that the updates on updatedProductoControles are not directly saved in db
        em.detach(updatedProductoControles);
        updatedProductoControles.descripcion(UPDATED_DESCRIPCION);
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(updatedProductoControles);

        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoControlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoControlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoControlesWithPatch() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();

        // Update the productoControles using partial update
        ProductoControles partialUpdatedProductoControles = new ProductoControles();
        partialUpdatedProductoControles.setId(productoControles.getId());

        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoControles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoControles))
            )
            .andExpect(status().isOk());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateProductoControlesWithPatch() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();

        // Update the productoControles using partial update
        ProductoControles partialUpdatedProductoControles = new ProductoControles();
        partialUpdatedProductoControles.setId(productoControles.getId());

        partialUpdatedProductoControles.descripcion(UPDATED_DESCRIPCION);

        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoControles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoControles))
            )
            .andExpect(status().isOk());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
        ProductoControles testProductoControles = productoControlesList.get(productoControlesList.size() - 1);
        assertThat(testProductoControles.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoControlesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoControles() throws Exception {
        int databaseSizeBeforeUpdate = productoControlesRepository.findAll().size();
        productoControles.setId(count.incrementAndGet());

        // Create the ProductoControles
        ProductoControlesDTO productoControlesDTO = productoControlesMapper.toDto(productoControles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoControlesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoControlesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoControles in the database
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoControles() throws Exception {
        // Initialize the database
        productoControlesRepository.saveAndFlush(productoControles);

        int databaseSizeBeforeDelete = productoControlesRepository.findAll().size();

        // Delete the productoControles
        restProductoControlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoControles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoControles> productoControlesList = productoControlesRepository.findAll();
        assertThat(productoControlesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
