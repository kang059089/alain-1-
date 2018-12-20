package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.service.DictService;
import com.bigcloud.alain.service.dto.DictDTO;
import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.Dict;
import com.bigcloud.alain.repository.DictRepository;
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

/**
 * REST controller for managing Dict.
 */
@RestController
@RequestMapping("/api")
public class DictResource {

    private final Logger log = LoggerFactory.getLogger(DictResource.class);

    private static final String ENTITY_NAME = "dict";

    private final DictRepository dictRepository;

    private final DictService dictService;

    public DictResource(DictRepository dictRepository, DictService dictService) {
        this.dictRepository = dictRepository;
        this.dictService = dictService;
    }

    /**
     * POST  /dicts : Create a new dict.
     *
     * @param dictDTO the dict to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dict, or with status 400 (Bad Request) if the dict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dicts")
    @Timed
    public ResponseEntity<Dict> createDict(@RequestBody DictDTO dictDTO) throws URISyntaxException {
        log.debug("创建字典传入的参数: {}", dictDTO);
        if (dictDTO.getId() != null) {
            throw new BadRequestAlertException("字典已存在", ENTITY_NAME, "idexists");
        } else if (dictService.hasOneByType(dictDTO.getType())) {
            throw new BadRequestAlertException("该字典类型已存在！", "menu.name", "name exists");
        }
        Dict dict = dictService.createrDict(dictDTO);
        Dict result = dictRepository.save(dict);
        return ResponseEntity.created(new URI("/api/dicts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dicts : Updates an existing dict.
     *
     * @param dictDTO the dict to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dict,
     * or with status 400 (Bad Request) if the dict is not valid,
     * or with status 500 (Internal Server Error) if the dict couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dicts")
    @Timed
    public ResponseEntity<Dict> updateDict(@RequestBody DictDTO dictDTO) throws URISyntaxException {
        log.debug("编辑字典传入的参数: {}", dictDTO);
        Optional<Dict> existingEntity = dictService.findOneByType(dictDTO.getType());
        if (dictDTO.getId() == null) {
            throw new BadRequestAlertException("字典不存在", ENTITY_NAME, "idnull");
        } else if (existingEntity.isPresent() && (!existingEntity.get().getType().equals(dictDTO.getType()))) {
            throw new BadRequestAlertException("该字典类型已存在！", "menu.name", "name exists");
        }
        Dict dict = dictService.createrDict(dictDTO);
        Dict result = dictRepository.save(dict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dict.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dicts : get all the dicts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dicts in body
     */
    @GetMapping("/dicts")
    @Timed
    public ResponseEntity<List<Dict>> getAllDicts(Pageable pageable) {
        log.debug("REST request to get a page of Dicts");
        Page<Dict> page = dictRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dicts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dicts/:id : get the "id" dict.
     *
     * @param id the id of the dict to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dict, or with status 404 (Not Found)
     */
    @GetMapping("/dicts/{id}")
    @Timed
    public ResponseEntity<Dict> getDict(@PathVariable Long id) {
        log.debug("REST request to get Dict : {}", id);
        Optional<Dict> dict = dictRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dict);
    }

    /**
     * DELETE  /dicts/:id : delete the "id" dict.
     *
     * @param id the id of the dict to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dicts/{id}")
    @Timed
    public ResponseEntity<Void> deleteDict(@PathVariable Long id) {
        log.debug("REST request to delete Dict : {}", id);

        dictRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     *  获取所有字典列表信息,返回字典分页信息
     * @param pageable 分页参数
     * @return 包括字典列表及字典总数的map集合
     */
    @GetMapping("/dictList")
    @Timed
    public ResponseEntity<Map> findDictList(Pageable pageable) {
        log.debug("获取所有字典列表分页信息的pageable参数: {}", pageable);
        Page<DictDTO> page = dictService.getDictsPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dictList");
        Map map = new HashMap();
        map.put("list",page.getContent());
        map.put("total",page.getTotalElements());
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    /**
     * 通过参数获取字典列表信息
     * @param item 参数（字典名称or字典类型）
     * @param pageable 分页参数
     * @return 包括字典列表及字典总数的map集合
     */
    @GetMapping("/dictSearch/{item}")
    @Timed
    public ResponseEntity<Map> getDictByItem(@PathVariable String item, Pageable pageable) {
        Page<DictDTO> page = dictService.getDictByItemPage(item, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dictSearch");
        Map map = new HashMap();
        map.put("list",page.getContent());
        map.put("total",page.getTotalElements());
        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }

    /**
     *  获取组织机构类型的字典信息
     * @return 组织机构类型字典信息
     */
    @GetMapping("/dictOrg")
    @Timed
    public DictDTO getDictOrg() {
        DictDTO dictDTO = new DictDTO(dictRepository.getDictOrg());
        return dictDTO;
    }

}
