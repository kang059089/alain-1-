package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.service.MenuService;
import com.bigcloud.alain.service.dto.AclTreeDTO;
import com.bigcloud.alain.service.dto.MenuDTO;
import com.bigcloud.alain.service.dto.MenuTreeDTO;
import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.Menu;
import com.bigcloud.alain.repository.MenuRepository;
import com.bigcloud.alain.web.rest.errors.BadRequestAlertException;
import com.bigcloud.alain.web.rest.util.HeaderUtil;
import com.bigcloud.alain.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Menu.
 */
@RestController
@RequestMapping("/api")
public class MenuResource {

    private final Logger log = LoggerFactory.getLogger(MenuResource.class);

    private static final String ENTITY_NAME = "menu";

    private final MenuRepository menuRepository;

    private final MenuService menuService;

    public MenuResource(MenuRepository menuRepository, MenuService menuService) {
        this.menuRepository = menuRepository;
        this.menuService = menuService;
    }

    /**
     * POST  /menus : Create a new menu.
     *
     * @param menuDTO the menu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menu, or with status 400 (Bad Request) if the menu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menus")
    @Timed
    public ResponseEntity<Menu> createMenu(@RequestBody MenuDTO menuDTO) throws URISyntaxException {
        log.debug("创建菜单，传入的参数: {}", menuDTO);
        // 如果传入参数menuDTO的pid等于id，或者pid与参数id的子菜单下其中一个id相同，则抛出异常。
        if (menuDTO.getPid() != null && (menuDTO.getPid().equals(menuDTO.getId()) ? true : false || menuService.isMenuTree(menuDTO))) {
            throw new BadRequestAlertException("不符合菜单树形逻辑！", ENTITY_NAME, "menuTreeError");
        }
        if (menuDTO.getId() != null) {
            throw new BadRequestAlertException("菜单已存在", ENTITY_NAME, "idexists");
        }
        Menu menu = menuService.createrMenu(menuDTO);
        Menu result = menuRepository.save(menu);
        return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menus : Updates an existing menu.
     *
     * @param menuDTO the menu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menu,
     * or with status 400 (Bad Request) if the menu is not valid,
     * or with status 500 (Internal Server Error) if the menu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menus")
    @Timed
    public ResponseEntity<Menu> updateMenu(@RequestBody MenuDTO menuDTO) throws URISyntaxException {
        log.debug("编辑菜单，传入的参数: {}", menuDTO);
        // 如果传入参数menuDTO的pid等于id，或者pid与参数id的子菜单下其中一个id相同，则抛出异常。
        if (menuDTO.getPid() != null && (menuDTO.getPid().equals(menuDTO.getId()) ? true : false || menuService.isMenuTree(menuDTO))) {
            throw new BadRequestAlertException("不符合菜单树形逻辑！", ENTITY_NAME, "menuTreeError");
        }
        if (menuDTO.getId() == null) {
            throw new BadRequestAlertException("菜单不存在", ENTITY_NAME, "id null");
        }
        Menu menu = menuService.createrMenu(menuDTO);
        Menu result = menuRepository.save(menu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menus : get all the menus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of menus in body
     */
    @GetMapping("/menus")
    @Timed
    public ResponseEntity<List<Menu>> getAllMenus(Pageable pageable) {
        log.debug("REST request to get a page of Menus");
        Page<Menu> page = menuRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /menus/:id : get the "id" menu.
     *
     * @param id the id of the menu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menu, or with status 404 (Not Found)
     */
    @GetMapping("/menus/{id}")
    @Timed
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        log.debug("REST request to get Menu : {}", id);
        Optional<Menu> menu = menuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(menu);
    }

    /**
     * DELETE  /menus/:id : delete the "id" menu.
     *
     * @param id the id of the menu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        log.debug("删除菜单，传入的参数id: {}", id);
        menuRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * 获取所有菜单列表树形数据信息,返回菜单分页信息
     * @param pageable 分页参数
     * @return 返回包含所有菜单列表树形数据及总数的Map集合
     */
    @GetMapping("/menuTreeList")
    @Timed
    public ResponseEntity<Map> findMenuTreeList(Pageable pageable) {
        log.debug("获取所有菜单列表树形数据分页信息的pageable参数: {}", pageable);
        Page<MenuDTO> page = menuService.findMenuPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menuTreeList");
        Map map = new HashMap();
        map.put("list",page.getContent());
        map.put("total",page.getTotalElements());
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    /**
     * 获取所有菜单树结构信息
     * @return 返回所有菜单树结构信息
     */
    @GetMapping("/menuTree")
    @Timed
    public List<MenuTreeDTO> findMenuTree() {
        log.debug("获取所有菜单树结构信息");
        List<MenuTreeDTO> menuTree = menuRepository.findMenu().stream().sorted().map(MenuTreeDTO::new).collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 通过参数(菜单名称)获取菜单列表信息
     * @param item 参数(菜单名称)
     * @param pageable 分页参数
     * @return 返回包括对应菜单列表及菜单总数的map集合
     */
    @GetMapping("/menuSearch/{item}")
    @Timed
    public ResponseEntity<Map> findMenuByItemPage(@PathVariable String item, Pageable pageable) {
        Page<MenuDTO> page = menuService.findMenuByItemPage(item, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menuSearch");
        Map map = new HashMap();
        map.put("list",page.getContent());
        map.put("total",page.getTotalElements());
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    /**
     * 获取所有菜单权限树结构信息
     * @return
     */
    @GetMapping("/aclMenuTree")
    @Timed
    public List<AclTreeDTO> findAclTree() {
        log.debug("获取所有权限树结构信息");
        List<AclTreeDTO> aclTree = menuRepository.findMenu().stream().map(AclTreeDTO::new).collect(Collectors.toList());
        return aclTree;
    }
}
