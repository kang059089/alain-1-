package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.AlainApp;

import com.bigcloud.alain.domain.DictType;
import com.bigcloud.alain.repository.DictTypeRepository;
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
 * Test class for the DictTypeResource REST controller.
 *
 * @see DictTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlainApp.class)
public class DictTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT = 1;
    private static final Integer UPDATED_SORT = 2;

    @Autowired
    private DictTypeRepository dictTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDictTypeMockMvc;

    private DictType dictType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DictTypeResource dictTypeResource = new DictTypeResource(dictTypeRepository);
        this.restDictTypeMockMvc = MockMvcBuilders.standaloneSetup(dictTypeResource)
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
    public static DictType createEntity(EntityManager em) {
        DictType dictType = new DictType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .sort(DEFAULT_SORT);
        return dictType;
    }

    @Before
    public void initTest() {
        dictType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictType() throws Exception {
        int databaseSizeBeforeCreate = dictTypeRepository.findAll().size();

        // Create the DictType
        restDictTypeMockMvc.perform(post("/api/dict-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictType)))
            .andExpect(status().isCreated());

        // Validate the DictType in the database
        List<DictType> dictTypeList = dictTypeRepository.findAll();
        assertThat(dictTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DictType testDictType = dictTypeList.get(dictTypeList.size() - 1);
        assertThat(testDictType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDictType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDictType.getSort()).isEqualTo(DEFAULT_SORT);
    }

    @Test
    @Transactional
    public void createDictTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictTypeRepository.findAll().size();

        // Create the DictType with an existing ID
        dictType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictTypeMockMvc.perform(post("/api/dict-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictType)))
            .andExpect(status().isBadRequest());

        // Validate the DictType in the database
        List<DictType> dictTypeList = dictTypeRepository.findAll();
        assertThat(dictTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDictTypes() throws Exception {
        // Initialize the database
        dictTypeRepository.saveAndFlush(dictType);

        // Get all the dictTypeList
        restDictTypeMockMvc.perform(get("/api/dict-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SORT)));
    }
    
    @Test
    @Transactional
    public void getDictType() throws Exception {
        // Initialize the database
        dictTypeRepository.saveAndFlush(dictType);

        // Get the dictType
        restDictTypeMockMvc.perform(get("/api/dict-types/{id}", dictType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dictType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SORT));
    }

    @Test
    @Transactional
    public void getNonExistingDictType() throws Exception {
        // Get the dictType
        restDictTypeMockMvc.perform(get("/api/dict-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictType() throws Exception {
        // Initialize the database
        dictTypeRepository.saveAndFlush(dictType);

        int databaseSizeBeforeUpdate = dictTypeRepository.findAll().size();

        // Update the dictType
        DictType updatedDictType = dictTypeRepository.findById(dictType.getId()).get();
        // Disconnect from session so that the updates on updatedDictType are not directly saved in db
        em.detach(updatedDictType);
        updatedDictType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .sort(UPDATED_SORT);

        restDictTypeMockMvc.perform(put("/api/dict-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDictType)))
            .andExpect(status().isOk());

        // Validate the DictType in the database
        List<DictType> dictTypeList = dictTypeRepository.findAll();
        assertThat(dictTypeList).hasSize(databaseSizeBeforeUpdate);
        DictType testDictType = dictTypeList.get(dictTypeList.size() - 1);
        assertThat(testDictType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDictType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDictType.getSort()).isEqualTo(UPDATED_SORT);
    }

    @Test
    @Transactional
    public void updateNonExistingDictType() throws Exception {
        int databaseSizeBeforeUpdate = dictTypeRepository.findAll().size();

        // Create the DictType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictTypeMockMvc.perform(put("/api/dict-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dictType)))
            .andExpect(status().isBadRequest());

        // Validate the DictType in the database
        List<DictType> dictTypeList = dictTypeRepository.findAll();
        assertThat(dictTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDictType() throws Exception {
        // Initialize the database
        dictTypeRepository.saveAndFlush(dictType);

        int databaseSizeBeforeDelete = dictTypeRepository.findAll().size();

        // Get the dictType
        restDictTypeMockMvc.perform(delete("/api/dict-types/{id}", dictType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DictType> dictTypeList = dictTypeRepository.findAll();
        assertThat(dictTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DictType.class);
        DictType dictType1 = new DictType();
        dictType1.setId(1L);
        DictType dictType2 = new DictType();
        dictType2.setId(dictType1.getId());
        assertThat(dictType1).isEqualTo(dictType2);
        dictType2.setId(2L);
        assertThat(dictType1).isNotEqualTo(dictType2);
        dictType1.setId(null);
        assertThat(dictType1).isNotEqualTo(dictType2);
    }
}
