package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.AlainApp;

import com.bigcloud.alain.domain.Button;
import com.bigcloud.alain.repository.ButtonRepository;
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
 * Test class for the ButtonResource REST controller.
 *
 * @see ButtonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlainApp.class)
public class ButtonResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACL = "AAAAAAAAAA";
    private static final String UPDATED_ACL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT = 1;
    private static final Integer UPDATED_SORT = 2;

    @Autowired
    private ButtonRepository buttonRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restButtonMockMvc;

    private Button button;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ButtonResource buttonResource = new ButtonResource(buttonRepository);
        this.restButtonMockMvc = MockMvcBuilders.standaloneSetup(buttonResource)
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
    public static Button createEntity(EntityManager em) {
        Button button = new Button()
            .name(DEFAULT_NAME)
            .acl(DEFAULT_ACL)
            .description(DEFAULT_DESCRIPTION)
            .sort(DEFAULT_SORT);
        return button;
    }

    @Before
    public void initTest() {
        button = createEntity(em);
    }

    @Test
    @Transactional
    public void createButton() throws Exception {
        int databaseSizeBeforeCreate = buttonRepository.findAll().size();

        // Create the Button
        restButtonMockMvc.perform(post("/api/buttons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(button)))
            .andExpect(status().isCreated());

        // Validate the Button in the database
        List<Button> buttonList = buttonRepository.findAll();
        assertThat(buttonList).hasSize(databaseSizeBeforeCreate + 1);
        Button testButton = buttonList.get(buttonList.size() - 1);
        assertThat(testButton.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testButton.getAcl()).isEqualTo(DEFAULT_ACL);
        assertThat(testButton.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testButton.getSort()).isEqualTo(DEFAULT_SORT);
    }

    @Test
    @Transactional
    public void createButtonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buttonRepository.findAll().size();

        // Create the Button with an existing ID
        button.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restButtonMockMvc.perform(post("/api/buttons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(button)))
            .andExpect(status().isBadRequest());

        // Validate the Button in the database
        List<Button> buttonList = buttonRepository.findAll();
        assertThat(buttonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllButtons() throws Exception {
        // Initialize the database
        buttonRepository.saveAndFlush(button);

        // Get all the buttonList
        restButtonMockMvc.perform(get("/api/buttons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(button.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].acl").value(hasItem(DEFAULT_ACL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SORT)));
    }
    
    @Test
    @Transactional
    public void getButton() throws Exception {
        // Initialize the database
        buttonRepository.saveAndFlush(button);

        // Get the button
        restButtonMockMvc.perform(get("/api/buttons/{id}", button.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(button.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.acl").value(DEFAULT_ACL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SORT));
    }

    @Test
    @Transactional
    public void getNonExistingButton() throws Exception {
        // Get the button
        restButtonMockMvc.perform(get("/api/buttons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateButton() throws Exception {
        // Initialize the database
        buttonRepository.saveAndFlush(button);

        int databaseSizeBeforeUpdate = buttonRepository.findAll().size();

        // Update the button
        Button updatedButton = buttonRepository.findById(button.getId()).get();
        // Disconnect from session so that the updates on updatedButton are not directly saved in db
        em.detach(updatedButton);
        updatedButton
            .name(UPDATED_NAME)
            .acl(UPDATED_ACL)
            .description(UPDATED_DESCRIPTION)
            .sort(UPDATED_SORT);

        restButtonMockMvc.perform(put("/api/buttons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedButton)))
            .andExpect(status().isOk());

        // Validate the Button in the database
        List<Button> buttonList = buttonRepository.findAll();
        assertThat(buttonList).hasSize(databaseSizeBeforeUpdate);
        Button testButton = buttonList.get(buttonList.size() - 1);
        assertThat(testButton.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testButton.getAcl()).isEqualTo(UPDATED_ACL);
        assertThat(testButton.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testButton.getSort()).isEqualTo(UPDATED_SORT);
    }

    @Test
    @Transactional
    public void updateNonExistingButton() throws Exception {
        int databaseSizeBeforeUpdate = buttonRepository.findAll().size();

        // Create the Button

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restButtonMockMvc.perform(put("/api/buttons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(button)))
            .andExpect(status().isBadRequest());

        // Validate the Button in the database
        List<Button> buttonList = buttonRepository.findAll();
        assertThat(buttonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteButton() throws Exception {
        // Initialize the database
        buttonRepository.saveAndFlush(button);

        int databaseSizeBeforeDelete = buttonRepository.findAll().size();

        // Get the button
        restButtonMockMvc.perform(delete("/api/buttons/{id}", button.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Button> buttonList = buttonRepository.findAll();
        assertThat(buttonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Button.class);
        Button button1 = new Button();
        button1.setId(1L);
        Button button2 = new Button();
        button2.setId(button1.getId());
        assertThat(button1).isEqualTo(button2);
        button2.setId(2L);
        assertThat(button1).isNotEqualTo(button2);
        button1.setId(null);
        assertThat(button1).isNotEqualTo(button2);
    }
}
