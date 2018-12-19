package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.AlainApp;

import com.bigcloud.alain.domain.Menu;
import com.bigcloud.alain.repository.MenuRepository;
import com.bigcloud.alain.service.MenuService;
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
 * Test class for the MenuResource REST controller.
 *
 * @see MenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlainApp.class)
public class MenuResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_I_18_N = "AAAAAAAAAA";
    private static final String UPDATED_I_18_N = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GROUP = false;
    private static final Boolean UPDATED_GROUP = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LINK_EXACT = false;
    private static final Boolean UPDATED_LINK_EXACT = true;

    private static final String DEFAULT_EXTERNAL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_BADGE = 1;
    private static final Integer UPDATED_BADGE = 2;

    private static final Boolean DEFAULT_BADGE_DOT = false;
    private static final Boolean UPDATED_BADGE_DOT = true;

    private static final String DEFAULT_BADGE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_BADGE_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIDE = false;
    private static final Boolean UPDATED_HIDE = true;

    private static final Boolean DEFAULT_HIDE_IN_BREADCRUMB = false;
    private static final Boolean UPDATED_HIDE_IN_BREADCRUMB = true;

    private static final String DEFAULT_ACL = "AAAAAAAAAA";
    private static final String UPDATED_ACL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHORTCUT = false;
    private static final Boolean UPDATED_SHORTCUT = true;

    private static final Boolean DEFAULT_SHORTCU_ROOT = false;
    private static final Boolean UPDATED_SHORTCU_ROOT = true;

    private static final Boolean DEFAULT_REUSE = false;
    private static final Boolean UPDATED_REUSE = true;

    private static final Integer DEFAULT_SORT = 1;
    private static final Integer UPDATED_SORT = 2;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMenuMockMvc;

    private Menu menu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenuResource menuResource = new MenuResource(menuRepository, menuService);
        this.restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
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
    public static Menu createEntity(EntityManager em) {
        Menu menu = new Menu()
            .name(DEFAULT_NAME)
            .i18n(DEFAULT_I_18_N)
            .group(DEFAULT_GROUP)
            .link(DEFAULT_LINK)
            .linkExact(DEFAULT_LINK_EXACT)
            .externalLink(DEFAULT_EXTERNAL_LINK)
            .target(DEFAULT_TARGET)
            .icon(DEFAULT_ICON)
            .badge(DEFAULT_BADGE)
            .badgeDot(DEFAULT_BADGE_DOT)
            .badgeStatus(DEFAULT_BADGE_STATUS)
            .hide(DEFAULT_HIDE)
            .hideInBreadcrumb(DEFAULT_HIDE_IN_BREADCRUMB)
            .acl(DEFAULT_ACL)
            .shortcut(DEFAULT_SHORTCUT)
            .shortcuRoot(DEFAULT_SHORTCU_ROOT)
            .reuse(DEFAULT_REUSE)
            .sort(DEFAULT_SORT);
        return menu;
    }

    @Before
    public void initTest() {
        menu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenu.geti18n()).isEqualTo(DEFAULT_I_18_N);
        assertThat(testMenu.isGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testMenu.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testMenu.isLinkExact()).isEqualTo(DEFAULT_LINK_EXACT);
        assertThat(testMenu.getExternalLink()).isEqualTo(DEFAULT_EXTERNAL_LINK);
        assertThat(testMenu.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testMenu.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMenu.getBadge()).isEqualTo(DEFAULT_BADGE);
        assertThat(testMenu.isBadgeDot()).isEqualTo(DEFAULT_BADGE_DOT);
        assertThat(testMenu.getBadgeStatus()).isEqualTo(DEFAULT_BADGE_STATUS);
        assertThat(testMenu.isHide()).isEqualTo(DEFAULT_HIDE);
        assertThat(testMenu.isHideInBreadcrumb()).isEqualTo(DEFAULT_HIDE_IN_BREADCRUMB);
        assertThat(testMenu.getAcl()).isEqualTo(DEFAULT_ACL);
        assertThat(testMenu.isShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testMenu.isShortcuRoot()).isEqualTo(DEFAULT_SHORTCU_ROOT);
        assertThat(testMenu.isReuse()).isEqualTo(DEFAULT_REUSE);
        assertThat(testMenu.getSort()).isEqualTo(DEFAULT_SORT);
    }

    @Test
    @Transactional
    public void createMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu with an existing ID
        menu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList
        restMenuMockMvc.perform(get("/api/menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].i18n").value(hasItem(DEFAULT_I_18_N.toString())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].linkExact").value(hasItem(DEFAULT_LINK_EXACT.booleanValue())))
            .andExpect(jsonPath("$.[*].externalLink").value(hasItem(DEFAULT_EXTERNAL_LINK.toString())))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].badge").value(hasItem(DEFAULT_BADGE)))
            .andExpect(jsonPath("$.[*].badgeDot").value(hasItem(DEFAULT_BADGE_DOT.booleanValue())))
            .andExpect(jsonPath("$.[*].badgeStatus").value(hasItem(DEFAULT_BADGE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].hide").value(hasItem(DEFAULT_HIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].hideInBreadcrumb").value(hasItem(DEFAULT_HIDE_IN_BREADCRUMB.booleanValue())))
            .andExpect(jsonPath("$.[*].acl").value(hasItem(DEFAULT_ACL.toString())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.booleanValue())))
            .andExpect(jsonPath("$.[*].shortcuRoot").value(hasItem(DEFAULT_SHORTCU_ROOT.booleanValue())))
            .andExpect(jsonPath("$.[*].reuse").value(hasItem(DEFAULT_REUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SORT)));
    }
    
    @Test
    @Transactional
    public void getMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.i18n").value(DEFAULT_I_18_N.toString()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.linkExact").value(DEFAULT_LINK_EXACT.booleanValue()))
            .andExpect(jsonPath("$.externalLink").value(DEFAULT_EXTERNAL_LINK.toString()))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.badge").value(DEFAULT_BADGE))
            .andExpect(jsonPath("$.badgeDot").value(DEFAULT_BADGE_DOT.booleanValue()))
            .andExpect(jsonPath("$.badgeStatus").value(DEFAULT_BADGE_STATUS.toString()))
            .andExpect(jsonPath("$.hide").value(DEFAULT_HIDE.booleanValue()))
            .andExpect(jsonPath("$.hideInBreadcrumb").value(DEFAULT_HIDE_IN_BREADCRUMB.booleanValue()))
            .andExpect(jsonPath("$.acl").value(DEFAULT_ACL.toString()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.booleanValue()))
            .andExpect(jsonPath("$.shortcuRoot").value(DEFAULT_SHORTCU_ROOT.booleanValue()))
            .andExpect(jsonPath("$.reuse").value(DEFAULT_REUSE.booleanValue()))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SORT));
    }

    @Test
    @Transactional
    public void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        Menu updatedMenu = menuRepository.findById(menu.getId()).get();
        // Disconnect from session so that the updates on updatedMenu are not directly saved in db
        em.detach(updatedMenu);
        updatedMenu
            .name(UPDATED_NAME)
            .i18n(UPDATED_I_18_N)
            .group(UPDATED_GROUP)
            .link(UPDATED_LINK)
            .linkExact(UPDATED_LINK_EXACT)
            .externalLink(UPDATED_EXTERNAL_LINK)
            .target(UPDATED_TARGET)
            .icon(UPDATED_ICON)
            .badge(UPDATED_BADGE)
            .badgeDot(UPDATED_BADGE_DOT)
            .badgeStatus(UPDATED_BADGE_STATUS)
            .hide(UPDATED_HIDE)
            .hideInBreadcrumb(UPDATED_HIDE_IN_BREADCRUMB)
            .acl(UPDATED_ACL)
            .shortcut(UPDATED_SHORTCUT)
            .shortcuRoot(UPDATED_SHORTCU_ROOT)
            .reuse(UPDATED_REUSE)
            .sort(UPDATED_SORT);

        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMenu)))
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenu.geti18n()).isEqualTo(UPDATED_I_18_N);
        assertThat(testMenu.isGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testMenu.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testMenu.isLinkExact()).isEqualTo(UPDATED_LINK_EXACT);
        assertThat(testMenu.getExternalLink()).isEqualTo(UPDATED_EXTERNAL_LINK);
        assertThat(testMenu.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testMenu.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMenu.getBadge()).isEqualTo(UPDATED_BADGE);
        assertThat(testMenu.isBadgeDot()).isEqualTo(UPDATED_BADGE_DOT);
        assertThat(testMenu.getBadgeStatus()).isEqualTo(UPDATED_BADGE_STATUS);
        assertThat(testMenu.isHide()).isEqualTo(UPDATED_HIDE);
        assertThat(testMenu.isHideInBreadcrumb()).isEqualTo(UPDATED_HIDE_IN_BREADCRUMB);
        assertThat(testMenu.getAcl()).isEqualTo(UPDATED_ACL);
        assertThat(testMenu.isShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testMenu.isShortcuRoot()).isEqualTo(UPDATED_SHORTCU_ROOT);
        assertThat(testMenu.isReuse()).isEqualTo(UPDATED_REUSE);
        assertThat(testMenu.getSort()).isEqualTo(UPDATED_SORT);
    }

    @Test
    @Transactional
    public void updateNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Create the Menu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Get the menu
        restMenuMockMvc.perform(delete("/api/menus/{id}", menu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = new Menu();
        menu1.setId(1L);
        Menu menu2 = new Menu();
        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);
        menu2.setId(2L);
        assertThat(menu1).isNotEqualTo(menu2);
        menu1.setId(null);
        assertThat(menu1).isNotEqualTo(menu2);
    }
}
