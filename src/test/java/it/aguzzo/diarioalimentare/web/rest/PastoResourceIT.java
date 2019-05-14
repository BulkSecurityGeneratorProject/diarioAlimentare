package it.aguzzo.diarioalimentare.web.rest;

import it.aguzzo.diarioalimentare.DiarioAlimentareApp;
import it.aguzzo.diarioalimentare.domain.Pasto;
import it.aguzzo.diarioalimentare.repository.PastoRepository;
import it.aguzzo.diarioalimentare.service.PastoService;
import it.aguzzo.diarioalimentare.service.dto.PastoDTO;
import it.aguzzo.diarioalimentare.service.mapper.PastoMapper;
import it.aguzzo.diarioalimentare.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static it.aguzzo.diarioalimentare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PastoResource} REST controller.
 */
@SpringBootTest(classes = DiarioAlimentareApp.class)
public class PastoResourceIT {

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QUANTITA = 1;
    private static final Integer UPDATED_QUANTITA = 2;

    @Autowired
    private PastoRepository pastoRepository;

    @Autowired
    private PastoMapper pastoMapper;

    @Autowired
    private PastoService pastoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPastoMockMvc;

    private Pasto pasto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PastoResource pastoResource = new PastoResource(pastoService);
        this.restPastoMockMvc = MockMvcBuilders.standaloneSetup(pastoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pasto createEntity(EntityManager em) {
        Pasto pasto = new Pasto()
            .data(DEFAULT_DATA)
            .quantita(DEFAULT_QUANTITA);
        return pasto;
    }

    @BeforeEach
    public void initTest() {
        pasto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPasto() throws Exception {
        int databaseSizeBeforeCreate = pastoRepository.findAll().size();

        // Create the Pasto
        PastoDTO pastoDTO = pastoMapper.toDto(pasto);
        restPastoMockMvc.perform(post("/api/pastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pastoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pasto in the database
        List<Pasto> pastoList = pastoRepository.findAll();
        assertThat(pastoList).hasSize(databaseSizeBeforeCreate + 1);
        Pasto testPasto = pastoList.get(pastoList.size() - 1);
        assertThat(testPasto.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testPasto.getQuantita()).isEqualTo(DEFAULT_QUANTITA);
    }

    @Test
    @Transactional
    public void createPastoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pastoRepository.findAll().size();

        // Create the Pasto with an existing ID
        pasto.setId(1L);
        PastoDTO pastoDTO = pastoMapper.toDto(pasto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPastoMockMvc.perform(post("/api/pastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pasto in the database
        List<Pasto> pastoList = pastoRepository.findAll();
        assertThat(pastoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPastos() throws Exception {
        // Initialize the database
        pastoRepository.saveAndFlush(pasto);

        // Get all the pastoList
        restPastoMockMvc.perform(get("/api/pastos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pasto.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].quantita").value(hasItem(DEFAULT_QUANTITA)));
    }
    
    @Test
    @Transactional
    public void getPasto() throws Exception {
        // Initialize the database
        pastoRepository.saveAndFlush(pasto);

        // Get the pasto
        restPastoMockMvc.perform(get("/api/pastos/{id}", pasto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pasto.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.quantita").value(DEFAULT_QUANTITA));
    }

    @Test
    @Transactional
    public void getNonExistingPasto() throws Exception {
        // Get the pasto
        restPastoMockMvc.perform(get("/api/pastos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePasto() throws Exception {
        // Initialize the database
        pastoRepository.saveAndFlush(pasto);

        int databaseSizeBeforeUpdate = pastoRepository.findAll().size();

        // Update the pasto
        Pasto updatedPasto = pastoRepository.findById(pasto.getId()).get();
        // Disconnect from session so that the updates on updatedPasto are not directly saved in db
        em.detach(updatedPasto);
        updatedPasto
            .data(UPDATED_DATA)
            .quantita(UPDATED_QUANTITA);
        PastoDTO pastoDTO = pastoMapper.toDto(updatedPasto);

        restPastoMockMvc.perform(put("/api/pastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pastoDTO)))
            .andExpect(status().isOk());

        // Validate the Pasto in the database
        List<Pasto> pastoList = pastoRepository.findAll();
        assertThat(pastoList).hasSize(databaseSizeBeforeUpdate);
        Pasto testPasto = pastoList.get(pastoList.size() - 1);
        assertThat(testPasto.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testPasto.getQuantita()).isEqualTo(UPDATED_QUANTITA);
    }

    @Test
    @Transactional
    public void updateNonExistingPasto() throws Exception {
        int databaseSizeBeforeUpdate = pastoRepository.findAll().size();

        // Create the Pasto
        PastoDTO pastoDTO = pastoMapper.toDto(pasto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPastoMockMvc.perform(put("/api/pastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pasto in the database
        List<Pasto> pastoList = pastoRepository.findAll();
        assertThat(pastoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePasto() throws Exception {
        // Initialize the database
        pastoRepository.saveAndFlush(pasto);

        int databaseSizeBeforeDelete = pastoRepository.findAll().size();

        // Delete the pasto
        restPastoMockMvc.perform(delete("/api/pastos/{id}", pasto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Pasto> pastoList = pastoRepository.findAll();
        assertThat(pastoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pasto.class);
        Pasto pasto1 = new Pasto();
        pasto1.setId(1L);
        Pasto pasto2 = new Pasto();
        pasto2.setId(pasto1.getId());
        assertThat(pasto1).isEqualTo(pasto2);
        pasto2.setId(2L);
        assertThat(pasto1).isNotEqualTo(pasto2);
        pasto1.setId(null);
        assertThat(pasto1).isNotEqualTo(pasto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PastoDTO.class);
        PastoDTO pastoDTO1 = new PastoDTO();
        pastoDTO1.setId(1L);
        PastoDTO pastoDTO2 = new PastoDTO();
        assertThat(pastoDTO1).isNotEqualTo(pastoDTO2);
        pastoDTO2.setId(pastoDTO1.getId());
        assertThat(pastoDTO1).isEqualTo(pastoDTO2);
        pastoDTO2.setId(2L);
        assertThat(pastoDTO1).isNotEqualTo(pastoDTO2);
        pastoDTO1.setId(null);
        assertThat(pastoDTO1).isNotEqualTo(pastoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pastoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pastoMapper.fromId(null)).isNull();
    }
}
