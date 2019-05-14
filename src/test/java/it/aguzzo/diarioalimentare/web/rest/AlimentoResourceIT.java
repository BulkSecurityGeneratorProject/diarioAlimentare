package it.aguzzo.diarioalimentare.web.rest;

import it.aguzzo.diarioalimentare.DiarioAlimentareApp;
import it.aguzzo.diarioalimentare.domain.Alimento;
import it.aguzzo.diarioalimentare.repository.AlimentoRepository;
import it.aguzzo.diarioalimentare.service.AlimentoService;
import it.aguzzo.diarioalimentare.service.dto.AlimentoDTO;
import it.aguzzo.diarioalimentare.service.mapper.AlimentoMapper;
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
import java.util.List;

import static it.aguzzo.diarioalimentare.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AlimentoResource} REST controller.
 */
@SpringBootTest(classes = DiarioAlimentareApp.class)
public class AlimentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private AlimentoMapper alimentoMapper;

    @Autowired
    private AlimentoService alimentoService;

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

    private MockMvc restAlimentoMockMvc;

    private Alimento alimento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlimentoResource alimentoResource = new AlimentoResource(alimentoService);
        this.restAlimentoMockMvc = MockMvcBuilders.standaloneSetup(alimentoResource)
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
    public static Alimento createEntity(EntityManager em) {
        Alimento alimento = new Alimento()
            .nome(DEFAULT_NOME)
            .descrizione(DEFAULT_DESCRIZIONE);
        return alimento;
    }

    @BeforeEach
    public void initTest() {
        alimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlimento() throws Exception {
        int databaseSizeBeforeCreate = alimentoRepository.findAll().size();

        // Create the Alimento
        AlimentoDTO alimentoDTO = alimentoMapper.toDto(alimento);
        restAlimentoMockMvc.perform(post("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Alimento testAlimento = alimentoList.get(alimentoList.size() - 1);
        assertThat(testAlimento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAlimento.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createAlimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alimentoRepository.findAll().size();

        // Create the Alimento with an existing ID
        alimento.setId(1L);
        AlimentoDTO alimentoDTO = alimentoMapper.toDto(alimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlimentoMockMvc.perform(post("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlimentos() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);

        // Get all the alimentoList
        restAlimentoMockMvc.perform(get("/api/alimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }
    
    @Test
    @Transactional
    public void getAlimento() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);

        // Get the alimento
        restAlimentoMockMvc.perform(get("/api/alimentos/{id}", alimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlimento() throws Exception {
        // Get the alimento
        restAlimentoMockMvc.perform(get("/api/alimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlimento() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);

        int databaseSizeBeforeUpdate = alimentoRepository.findAll().size();

        // Update the alimento
        Alimento updatedAlimento = alimentoRepository.findById(alimento.getId()).get();
        // Disconnect from session so that the updates on updatedAlimento are not directly saved in db
        em.detach(updatedAlimento);
        updatedAlimento
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE);
        AlimentoDTO alimentoDTO = alimentoMapper.toDto(updatedAlimento);

        restAlimentoMockMvc.perform(put("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeUpdate);
        Alimento testAlimento = alimentoList.get(alimentoList.size() - 1);
        assertThat(testAlimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAlimento.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlimento() throws Exception {
        int databaseSizeBeforeUpdate = alimentoRepository.findAll().size();

        // Create the Alimento
        AlimentoDTO alimentoDTO = alimentoMapper.toDto(alimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlimentoMockMvc.perform(put("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlimento() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);

        int databaseSizeBeforeDelete = alimentoRepository.findAll().size();

        // Delete the alimento
        restAlimentoMockMvc.perform(delete("/api/alimentos/{id}", alimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alimento.class);
        Alimento alimento1 = new Alimento();
        alimento1.setId(1L);
        Alimento alimento2 = new Alimento();
        alimento2.setId(alimento1.getId());
        assertThat(alimento1).isEqualTo(alimento2);
        alimento2.setId(2L);
        assertThat(alimento1).isNotEqualTo(alimento2);
        alimento1.setId(null);
        assertThat(alimento1).isNotEqualTo(alimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlimentoDTO.class);
        AlimentoDTO alimentoDTO1 = new AlimentoDTO();
        alimentoDTO1.setId(1L);
        AlimentoDTO alimentoDTO2 = new AlimentoDTO();
        assertThat(alimentoDTO1).isNotEqualTo(alimentoDTO2);
        alimentoDTO2.setId(alimentoDTO1.getId());
        assertThat(alimentoDTO1).isEqualTo(alimentoDTO2);
        alimentoDTO2.setId(2L);
        assertThat(alimentoDTO1).isNotEqualTo(alimentoDTO2);
        alimentoDTO1.setId(null);
        assertThat(alimentoDTO1).isNotEqualTo(alimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alimentoMapper.fromId(null)).isNull();
    }
}
