package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.service.OrgService;
import com.bigcloud.alain.service.dto.OrgDTO;
import com.bigcloud.alain.service.dto.OrgTreeDTO;
import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.Org;
import com.bigcloud.alain.repository.OrgRepository;
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
 * REST controller for managing Org.
 */
@RestController
@RequestMapping("/api")
public class OrgResource {

    private final Logger log = LoggerFactory.getLogger(OrgResource.class);

    private static final String ENTITY_NAME = "org";

    private final OrgRepository orgRepository;

    private final OrgService orgService;

    public OrgResource(OrgRepository orgRepository, OrgService orgService) {
        this.orgRepository = orgRepository;
        this.orgService = orgService;
    }

    /**
     * POST  /orgs : Create a new org.
     *
     * @param orgDTO the org to create
     * @return the ResponseEntity with status 201 (Created) and with body the new org, or with status 400 (Bad Request) if the org has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orgs")
    @Timed
    public ResponseEntity<Org> createOrg(@RequestBody OrgDTO orgDTO) throws URISyntaxException {
        log.debug("创建组织机构，传入的参数: {}", orgDTO);
        // 如果传入参数OrgDTO的pid等于id，或者pid与参数id的子组织下其中一个id相同，则抛出异常。
        if (orgDTO.getPid() != null && (orgDTO.getPid().equals(orgDTO.getId()) ? true : false || orgService.isOrgTree(orgDTO))) {
            throw new BadRequestAlertException("不符合组织机构树形逻辑！", ENTITY_NAME, "orgTreeError");
        }
        if (orgDTO.getId() != null) {
            throw new BadRequestAlertException("组织机构已存在", ENTITY_NAME, "idexists");
        }
        Org org = orgService.createrOrg(orgDTO);
        Org result = orgRepository.save(org);
        return ResponseEntity.created(new URI("/api/orgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orgs : Updates an existing org.
     *
     * @param orgDTO the org to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated org,
     * or with status 400 (Bad Request) if the org is not valid,
     * or with status 500 (Internal Server Error) if the org couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orgs")
    @Timed
    public ResponseEntity<Org> updateOrg(@RequestBody OrgDTO orgDTO) throws URISyntaxException {
        log.debug("编辑组织机构，传入的参数: {}", orgDTO);
        // 如果传入参数OrgDTO的pid等于id，或者pid与参数id的子组织下其中一个id相同，则抛出异常。
        if (orgDTO.getPid() != null && (orgDTO.getPid().equals(orgDTO.getId()) ? true : false || orgService.isOrgTree(orgDTO))) {
            throw new BadRequestAlertException("不符合组织机构树形逻辑！", ENTITY_NAME, "orgTreeError");
        }
        if (orgDTO.getId() == null) {
            throw new BadRequestAlertException("组织机构已存在", ENTITY_NAME, "idnull");
        }
        Org org = orgService.createrOrg(orgDTO);
        Org result = orgRepository.save(org);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, org.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orgs : get all the orgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orgs in body
     */
    @GetMapping("/orgs")
    @Timed
    public ResponseEntity<List<Org>> getAllOrgs(Pageable pageable) {
        log.debug("REST request to get a page of Orgs");
        Page<Org> page = orgRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orgs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /orgs/:id : get the "id" org.
     *
     * @param id the id of the org to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the org, or with status 404 (Not Found)
     */
    @GetMapping("/orgs/{id}")
    @Timed
    public ResponseEntity<Org> getOrg(@PathVariable Long id) {
        log.debug("REST request to get Org : {}", id);
        Optional<Org> org = orgRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(org);
    }

    /**
     * DELETE  /orgs/:id : delete the "id" org.
     *
     * @param id the id of the org to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrg(@PathVariable Long id) {
        log.debug("REST request to delete Org : {}", id);

        orgRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * 获取所有组织机构列表
     *
     * @param pageable 分页参数
     * @return 返回包含所有组织机构列表及总数的Map集合
     */
    @GetMapping("/orgList")
    @Timed
    public ResponseEntity<Map> findOrgList(Pageable pageable) {
        log.debug("获取所有组织机构列表分页信息的pageable参数: {}", pageable);
        Page<OrgDTO> page = orgService.findOrgPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orgList");
        Map map = new HashMap();
        map.put("list", page.getContent());
        map.put("total", page.getTotalElements());
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    /**
     * 获取所有组织机构树结构信息
     * @return 返回所有组织机构树结构信息
     */
    @GetMapping("/orgTree")
    @Timed
    public List<OrgTreeDTO> findOrgTree() {
        log.debug("获取所有组织机构树结构信息");
        List<OrgTreeDTO> orgTree = orgRepository.findOrg().stream().sorted().map(OrgTreeDTO::new).collect(Collectors.toList());
        return orgTree;
    }

    /**
     * 通过参数获取组织机构列表信息
     * @param item 参数（机构名称or机构编码or机构id）
     * @param pageable 分页参数
     * @return 返回包括组织机构列表及组织机构总数的map集合
     */
    @GetMapping("/orgSearch/{item}")
    @Timed
    public ResponseEntity<Map> getByItem(@PathVariable String item, Pageable pageable) {
        Page<OrgDTO> page = orgService.getOrgByItemPage(item, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orgSearch");
        Map map = new HashMap();
        map.put("list",page.getContent());
        map.put("total",page.getTotalElements());
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

}
