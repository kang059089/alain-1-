package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.AlainApp;

import com.bigcloud.alain.domain.Org;
import com.bigcloud.alain.repository.OrgRepository;
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
 * Test class for the OrgResource REST controller.
 *
 * @see OrgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlainApp.class)
public class OrgResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT = 1;
    private static final Integer UPDATED_SORT = 2;

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrgMockMvc;

    private Org org;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrgResource orgResource = new OrgResource(orgRepository);
        this.restOrgMockMvc = MockMvcBuilders.standaloneSetup(orgResource)
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
    public static Org createEntity(EntityManager em) {
        Org org = new Org()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .telephone(DEFAULT_TELEPHONE)
            .fax(DEFAULT_FAX)
            .address(DEFAULT_ADDRESS)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .icon(DEFAULT_ICON)
            .description(DEFAULT_DESCRIPTION)
            .sort(DEFAULT_SORT);
        return org;
    }

    @Before
    public void initTest() {
        org = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrg() throws Exception {
        int databaseSizeBeforeCreate = orgRepository.findAll().size();

        // Create the Org
        restOrgMockMvc.perform(post("/api/orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(org)))
            .andExpect(status().isCreated());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeCreate + 1);
        Org testOrg = orgList.get(orgList.size() - 1);
        assertThat(testOrg.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrg.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrg.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOrg.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testOrg.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrg.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testOrg.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testOrg.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testOrg.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrg.getSort()).isEqualTo(DEFAULT_SORT);
    }

    @Test
    @Transactional
    public void createOrgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orgRepository.findAll().size();

        // Create the Org with an existing ID
        org.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgMockMvc.perform(post("/api/orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(org)))
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrgs() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        // Get all the orgList
        restOrgMockMvc.perform(get("/api/orgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(org.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SORT)));
    }
    
    @Test
    @Transactional
    public void getOrg() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        // Get the org
        restOrgMockMvc.perform(get("/api/orgs/{id}", org.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(org.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SORT));
    }

    @Test
    @Transactional
    public void getNonExistingOrg() throws Exception {
        // Get the org
        restOrgMockMvc.perform(get("/api/orgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrg() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        int databaseSizeBeforeUpdate = orgRepository.findAll().size();

        // Update the org
        Org updatedOrg = orgRepository.findById(org.getId()).get();
        // Disconnect from session so that the updates on updatedOrg are not directly saved in db
        em.detach(updatedOrg);
        updatedOrg
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .address(UPDATED_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .icon(UPDATED_ICON)
            .description(UPDATED_DESCRIPTION)
            .sort(UPDATED_SORT);

        restOrgMockMvc.perform(put("/api/orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrg)))
            .andExpect(status().isOk());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
        Org testOrg = orgList.get(orgList.size() - 1);
        assertThat(testOrg.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrg.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrg.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOrg.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrg.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrg.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testOrg.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testOrg.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testOrg.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrg.getSort()).isEqualTo(UPDATED_SORT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrg() throws Exception {
        int databaseSizeBeforeUpdate = orgRepository.findAll().size();

        // Create the Org

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgMockMvc.perform(put("/api/orgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(org)))
            .andExpect(status().isBadRequest());

        // Validate the Org in the database
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrg() throws Exception {
        // Initialize the database
        orgRepository.saveAndFlush(org);

        int databaseSizeBeforeDelete = orgRepository.findAll().size();

        // Get the org
        restOrgMockMvc.perform(delete("/api/orgs/{id}", org.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Org> orgList = orgRepository.findAll();
        assertThat(orgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Org.class);
        Org org1 = new Org();
        org1.setId(1L);
        Org org2 = new Org();
        org2.setId(org1.getId());
        assertThat(org1).isEqualTo(org2);
        org2.setId(2L);
        assertThat(org1).isNotEqualTo(org2);
        org1.setId(null);
        assertThat(org1).isNotEqualTo(org2);
    }
}
