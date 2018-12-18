package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.AlainApp;

import com.bigcloud.alain.domain.Dict;
import com.bigcloud.alain.repository.DictRepository;
import com.bigcloud.alain.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.bigcloud.alain.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DictResource REST controller.
 *
 * @see DictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlainApp.class)
public class DictResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDictMockMvc;

    private Dict dict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DictResource dictResource = new DictResource(dictRepository);
        this.restDictMockMvc = MockMvcBuilders.standaloneSetup(dictResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dict createEntity(EntityManager em) {
        Dict dict = new Dict()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return dict;
    }

    @Before
    public void initTest() {
        dict = createEntity(em);
    }

    @Test
    @Transactional
    public void createDict() throws Exception {
        int databaseSizeBeforeCreate = dictRepository.findAll().size();

        // Create the Dict
        restDictMockMvc.perform(post("/api/dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dict)))
            .andExpect(status().isCreated());

        // Validate the Dict in the database
        List<Dict> dictList = dictRepository.findAll();
        assertThat(dictList).hasSize(databaseSizeBeforeCreate + 1);
        Dict testDict = dictList.get(dictList.size() - 1);
        assertThat(testDict.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDict.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createDictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictRepository.findAll().size();

        // Create the Dict with an existing ID
        dict.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictMockMvc.perform(post("/api/dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dict)))
            .andExpect(status().isBadRequest());

        // Validate the Dict in the database
        List<Dict> dictList = dictRepository.findAll();
        assertThat(dictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDicts() throws Exception {
        // Initialize the database
        dictRepository.saveAndFlush(dict);

        // Get all the dictList
        restDictMockMvc.perform(get("/api/dicts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dict.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDict() throws Exception {
        // Initialize the database
        dictRepository.saveAndFlush(dict);

        // Get the dict
        restDictMockMvc.perform(get("/api/dicts/{id}", dict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dict.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDict() throws Exception {
        // Get the dict
        restDictMockMvc.perform(get("/api/dicts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDict() throws Exception {
        // Initialize the database
        dictRepository.saveAndFlush(dict);

        int databaseSizeBeforeUpdate = dictRepository.findAll().size();

        // Update the dict
        Dict updatedDict = dictRepository.findById(dict.getId()).get();
        // Disconnect from session so that the updates on updatedDict are not directly saved in db
        em.detach(updatedDict);
        updatedDict
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);

        restDictMockMvc.perform(put("/api/dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDict)))
            .andExpect(status().isOk());

        // Validate the Dict in the database
        List<Dict> dictList = dictRepository.findAll();
        assertThat(dictList).hasSize(databaseSizeBeforeUpdate);
        Dict testDict = dictList.get(dictList.size() - 1);
        assertThat(testDict.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDict.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDict() throws Exception {
        int databaseSizeBeforeUpdate = dictRepository.findAll().size();

        // Create the Dict

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictMockMvc.perform(put("/api/dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dict)))
            .andExpect(status().isBadRequest());

        // Validate the Dict in the database
        List<Dict> dictList = dictRepository.findAll();
        assertThat(dictList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDict() throws Exception {
        // Initialize the database
        dictRepository.saveAndFlush(dict);

        int databaseSizeBeforeDelete = dictRepository.findAll().size();

        // Get the dict
        restDictMockMvc.perform(delete("/api/dicts/{id}", dict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dict> dictList = dictRepository.findAll();
        assertThat(dictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dict.class);
        Dict dict1 = new Dict();
        dict1.setId(1L);
        Dict dict2 = new Dict();
        dict2.setId(dict1.getId());
        assertThat(dict1).isEqualTo(dict2);
        dict2.setId(2L);
        assertThat(dict1).isNotEqualTo(dict2);
        dict1.setId(null);
        assertThat(dict1).isNotEqualTo(dict2);
    }
}
