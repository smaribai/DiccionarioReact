package com.santi.dicccionario.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.santi.dicccionario.IntegrationTest;
import com.santi.dicccionario.domain.Envio;
import com.santi.dicccionario.repository.EnvioRepository;
import com.santi.dicccionario.service.dto.EnvioDTO;
import com.santi.dicccionario.service.mapper.EnvioMapper;
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
 * Integration tests for the {@link EnvioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnvioResourceIT {

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_REMITENTE = "AAAAAAAAAA";
    private static final String UPDATED_REMITENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATARIO = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATARIO = "BBBBBBBBBB";

    private static final String DEFAULT_PROVEEDOR = "AAAAAAAAAA";
    private static final String UPDATED_PROVEEDOR = "BBBBBBBBBB";

    private static final String DEFAULT_REF_REMITENTE = "AAAAAAAAAA";
    private static final String UPDATED_REF_REMITENTE = "BBBBBBBBBB";

    private static final String DEFAULT_REF_DESTINATARIO = "AAAAAAAAAA";
    private static final String UPDATED_REF_DESTINATARIO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_ARANCELARIO_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ARANCELARIO_ORIGEN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_IMPORTE = 1D;
    private static final Double UPDATED_IMPORTE = 2D;

    private static final String DEFAULT_PROVINCIA_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA_DESTINO = "BBBBBBBBBB";

    private static final Integer DEFAULT_UDS = 1;
    private static final Integer UPDATED_UDS = 2;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final String ENTITY_API_URL = "/api/envios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private EnvioMapper envioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvioMockMvc;

    private Envio envio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Envio createEntity(EntityManager em) {
        Envio envio = new Envio()
            .cliente(DEFAULT_CLIENTE)
            .remitente(DEFAULT_REMITENTE)
            .destinatario(DEFAULT_DESTINATARIO)
            .proveedor(DEFAULT_PROVEEDOR)
            .refRemitente(DEFAULT_REF_REMITENTE)
            .refDestinatario(DEFAULT_REF_DESTINATARIO)
            .codigoArancelarioOrigen(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)
            .descripcion(DEFAULT_DESCRIPCION)
            .importe(DEFAULT_IMPORTE)
            .provinciaDestino(DEFAULT_PROVINCIA_DESTINO)
            .uds(DEFAULT_UDS)
            .peso(DEFAULT_PESO);
        return envio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Envio createUpdatedEntity(EntityManager em) {
        Envio envio = new Envio()
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .refRemitente(UPDATED_REF_REMITENTE)
            .refDestinatario(UPDATED_REF_DESTINATARIO)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .descripcion(UPDATED_DESCRIPCION)
            .importe(UPDATED_IMPORTE)
            .provinciaDestino(UPDATED_PROVINCIA_DESTINO)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO);
        return envio;
    }

    @BeforeEach
    public void initTest() {
        envio = createEntity(em);
    }

    @Test
    @Transactional
    void createEnvio() throws Exception {
        int databaseSizeBeforeCreate = envioRepository.findAll().size();
        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);
        restEnvioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(envioDTO)))
            .andExpect(status().isCreated());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeCreate + 1);
        Envio testEnvio = envioList.get(envioList.size() - 1);
        assertThat(testEnvio.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testEnvio.getRemitente()).isEqualTo(DEFAULT_REMITENTE);
        assertThat(testEnvio.getDestinatario()).isEqualTo(DEFAULT_DESTINATARIO);
        assertThat(testEnvio.getProveedor()).isEqualTo(DEFAULT_PROVEEDOR);
        assertThat(testEnvio.getRefRemitente()).isEqualTo(DEFAULT_REF_REMITENTE);
        assertThat(testEnvio.getRefDestinatario()).isEqualTo(DEFAULT_REF_DESTINATARIO);
        assertThat(testEnvio.getCodigoArancelarioOrigen()).isEqualTo(DEFAULT_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testEnvio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEnvio.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testEnvio.getProvinciaDestino()).isEqualTo(DEFAULT_PROVINCIA_DESTINO);
        assertThat(testEnvio.getUds()).isEqualTo(DEFAULT_UDS);
        assertThat(testEnvio.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void createEnvioWithExistingId() throws Exception {
        // Create the Envio with an existing ID
        envio.setId(1L);
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        int databaseSizeBeforeCreate = envioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(envioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnvios() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        // Get all the envioList
        restEnvioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envio.getId().intValue())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].remitente").value(hasItem(DEFAULT_REMITENTE)))
            .andExpect(jsonPath("$.[*].destinatario").value(hasItem(DEFAULT_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].refRemitente").value(hasItem(DEFAULT_REF_REMITENTE)))
            .andExpect(jsonPath("$.[*].refDestinatario").value(hasItem(DEFAULT_REF_DESTINATARIO)))
            .andExpect(jsonPath("$.[*].codigoArancelarioOrigen").value(hasItem(DEFAULT_CODIGO_ARANCELARIO_ORIGEN)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].provinciaDestino").value(hasItem(DEFAULT_PROVINCIA_DESTINO)))
            .andExpect(jsonPath("$.[*].uds").value(hasItem(DEFAULT_UDS)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())));
    }

    @Test
    @Transactional
    void getEnvio() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        // Get the envio
        restEnvioMockMvc
            .perform(get(ENTITY_API_URL_ID, envio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(envio.getId().intValue()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.remitente").value(DEFAULT_REMITENTE))
            .andExpect(jsonPath("$.destinatario").value(DEFAULT_DESTINATARIO))
            .andExpect(jsonPath("$.proveedor").value(DEFAULT_PROVEEDOR))
            .andExpect(jsonPath("$.refRemitente").value(DEFAULT_REF_REMITENTE))
            .andExpect(jsonPath("$.refDestinatario").value(DEFAULT_REF_DESTINATARIO))
            .andExpect(jsonPath("$.codigoArancelarioOrigen").value(DEFAULT_CODIGO_ARANCELARIO_ORIGEN))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.doubleValue()))
            .andExpect(jsonPath("$.provinciaDestino").value(DEFAULT_PROVINCIA_DESTINO))
            .andExpect(jsonPath("$.uds").value(DEFAULT_UDS))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingEnvio() throws Exception {
        // Get the envio
        restEnvioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnvio() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        int databaseSizeBeforeUpdate = envioRepository.findAll().size();

        // Update the envio
        Envio updatedEnvio = envioRepository.findById(envio.getId()).get();
        // Disconnect from session so that the updates on updatedEnvio are not directly saved in db
        em.detach(updatedEnvio);
        updatedEnvio
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .refRemitente(UPDATED_REF_REMITENTE)
            .refDestinatario(UPDATED_REF_DESTINATARIO)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .descripcion(UPDATED_DESCRIPCION)
            .importe(UPDATED_IMPORTE)
            .provinciaDestino(UPDATED_PROVINCIA_DESTINO)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO);
        EnvioDTO envioDTO = envioMapper.toDto(updatedEnvio);

        restEnvioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, envioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(envioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
        Envio testEnvio = envioList.get(envioList.size() - 1);
        assertThat(testEnvio.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testEnvio.getRemitente()).isEqualTo(UPDATED_REMITENTE);
        assertThat(testEnvio.getDestinatario()).isEqualTo(UPDATED_DESTINATARIO);
        assertThat(testEnvio.getProveedor()).isEqualTo(UPDATED_PROVEEDOR);
        assertThat(testEnvio.getRefRemitente()).isEqualTo(UPDATED_REF_REMITENTE);
        assertThat(testEnvio.getRefDestinatario()).isEqualTo(UPDATED_REF_DESTINATARIO);
        assertThat(testEnvio.getCodigoArancelarioOrigen()).isEqualTo(UPDATED_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testEnvio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEnvio.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testEnvio.getProvinciaDestino()).isEqualTo(UPDATED_PROVINCIA_DESTINO);
        assertThat(testEnvio.getUds()).isEqualTo(UPDATED_UDS);
        assertThat(testEnvio.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void putNonExistingEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();
        envio.setId(count.incrementAndGet());

        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, envioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(envioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();
        envio.setId(count.incrementAndGet());

        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(envioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();
        envio.setId(count.incrementAndGet());

        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(envioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnvioWithPatch() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        int databaseSizeBeforeUpdate = envioRepository.findAll().size();

        // Update the envio using partial update
        Envio partialUpdatedEnvio = new Envio();
        partialUpdatedEnvio.setId(envio.getId());

        partialUpdatedEnvio
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .refDestinatario(UPDATED_REF_DESTINATARIO)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .descripcion(UPDATED_DESCRIPCION)
            .provinciaDestino(UPDATED_PROVINCIA_DESTINO);

        restEnvioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnvio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnvio))
            )
            .andExpect(status().isOk());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
        Envio testEnvio = envioList.get(envioList.size() - 1);
        assertThat(testEnvio.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testEnvio.getRemitente()).isEqualTo(UPDATED_REMITENTE);
        assertThat(testEnvio.getDestinatario()).isEqualTo(UPDATED_DESTINATARIO);
        assertThat(testEnvio.getProveedor()).isEqualTo(DEFAULT_PROVEEDOR);
        assertThat(testEnvio.getRefRemitente()).isEqualTo(DEFAULT_REF_REMITENTE);
        assertThat(testEnvio.getRefDestinatario()).isEqualTo(UPDATED_REF_DESTINATARIO);
        assertThat(testEnvio.getCodigoArancelarioOrigen()).isEqualTo(UPDATED_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testEnvio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEnvio.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testEnvio.getProvinciaDestino()).isEqualTo(UPDATED_PROVINCIA_DESTINO);
        assertThat(testEnvio.getUds()).isEqualTo(DEFAULT_UDS);
        assertThat(testEnvio.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void fullUpdateEnvioWithPatch() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        int databaseSizeBeforeUpdate = envioRepository.findAll().size();

        // Update the envio using partial update
        Envio partialUpdatedEnvio = new Envio();
        partialUpdatedEnvio.setId(envio.getId());

        partialUpdatedEnvio
            .cliente(UPDATED_CLIENTE)
            .remitente(UPDATED_REMITENTE)
            .destinatario(UPDATED_DESTINATARIO)
            .proveedor(UPDATED_PROVEEDOR)
            .refRemitente(UPDATED_REF_REMITENTE)
            .refDestinatario(UPDATED_REF_DESTINATARIO)
            .codigoArancelarioOrigen(UPDATED_CODIGO_ARANCELARIO_ORIGEN)
            .descripcion(UPDATED_DESCRIPCION)
            .importe(UPDATED_IMPORTE)
            .provinciaDestino(UPDATED_PROVINCIA_DESTINO)
            .uds(UPDATED_UDS)
            .peso(UPDATED_PESO);

        restEnvioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnvio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnvio))
            )
            .andExpect(status().isOk());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
        Envio testEnvio = envioList.get(envioList.size() - 1);
        assertThat(testEnvio.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testEnvio.getRemitente()).isEqualTo(UPDATED_REMITENTE);
        assertThat(testEnvio.getDestinatario()).isEqualTo(UPDATED_DESTINATARIO);
        assertThat(testEnvio.getProveedor()).isEqualTo(UPDATED_PROVEEDOR);
        assertThat(testEnvio.getRefRemitente()).isEqualTo(UPDATED_REF_REMITENTE);
        assertThat(testEnvio.getRefDestinatario()).isEqualTo(UPDATED_REF_DESTINATARIO);
        assertThat(testEnvio.getCodigoArancelarioOrigen()).isEqualTo(UPDATED_CODIGO_ARANCELARIO_ORIGEN);
        assertThat(testEnvio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEnvio.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testEnvio.getProvinciaDestino()).isEqualTo(UPDATED_PROVINCIA_DESTINO);
        assertThat(testEnvio.getUds()).isEqualTo(UPDATED_UDS);
        assertThat(testEnvio.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void patchNonExistingEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();
        envio.setId(count.incrementAndGet());

        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, envioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(envioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();
        envio.setId(count.incrementAndGet());

        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(envioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnvio() throws Exception {
        int databaseSizeBeforeUpdate = envioRepository.findAll().size();
        envio.setId(count.incrementAndGet());

        // Create the Envio
        EnvioDTO envioDTO = envioMapper.toDto(envio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(envioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Envio in the database
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnvio() throws Exception {
        // Initialize the database
        envioRepository.saveAndFlush(envio);

        int databaseSizeBeforeDelete = envioRepository.findAll().size();

        // Delete the envio
        restEnvioMockMvc
            .perform(delete(ENTITY_API_URL_ID, envio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Envio> envioList = envioRepository.findAll();
        assertThat(envioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
