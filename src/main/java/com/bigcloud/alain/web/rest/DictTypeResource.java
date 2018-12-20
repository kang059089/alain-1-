package com.bigcloud.alain.web.rest;

import com.bigcloud.alain.service.DictTypeService;
import com.bigcloud.alain.service.dto.DictTypeDTO;
import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.DictType;
import com.bigcloud.alain.repository.DictTypeRepository;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DictType.
 */
@RestController
@RequestMapping("/api")
public class DictTypeResource {

    private final Logger log = LoggerFactory.getLogger(DictTypeResource.class);

    private static final String ENTITY_NAME = "dictType";

    private final DictTypeRepository dictTypeRepository;

    private final DictTypeService dictTypeService;

    public DictTypeResource(DictTypeRepository dictTypeRepository, DictTypeService dictTypeService) {
        this.dictTypeRepository = dictTypeRepository;
        this.dictTypeService = dictTypeService;
    }

    /**
     * POST  /dict-types : Create a new dictType.
     *
     * @param dictTypeDTO the dictType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dictType, or with status 400 (Bad Request) if the dictType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dict-types")
    @Timed
    public ResponseEntity<DictType> createDictType(@RequestBody DictTypeDTO dictTypeDTO) throws URISyntaxException {
        log.debug("创建字典类型传入的参数: {}", dictTypeDTO);
        if (dictTypeDTO.getId() != null) {
            throw new BadRequestAlertException("字典类型已存在", ENTITY_NAME, "idexists");
        } else if (dictTypeService.hasOneByCode(dictTypeDTO)) {
            throw new BadRequestAlertException("该字典类型编码已存在！", "menu.name", "name exists");
        }
        DictType dictType = dictTypeService.createrDict(dictTypeDTO);
        DictType result = dictTypeRepository.save(dictType);
        return ResponseEntity.created(new URI("/api/dict-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dict-types : Updates an existing dictType.
     *
     * @param dictTypeDTO the dictType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dictType,
     * or with status 400 (Bad Request) if the dictType is not valid,
     * or with status 500 (Internal Server Error) if the dictType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dict-types")
    @Timed
    public ResponseEntity<DictType> updateDictType(@RequestBody DictTypeDTO dictTypeDTO) throws URISyntaxException {
        log.debug("编辑字典类型传入的参数: {}", dictTypeDTO);
        if (dictTypeDTO.getId() == null) {
            throw new BadRequestAlertException("字典类型不存在", ENTITY_NAME, "idnull");
        } else if (dictTypeService.findOneByCode(dictTypeDTO)) {
            throw new BadRequestAlertException("该字典类型编码已存在！", "menu.name", "name exists");
        }
        DictType dictType = dictTypeService.createrDict(dictTypeDTO);
        DictType result = dictTypeRepository.save(dictType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dictType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dict-types : get all the dictTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dictTypes in body
     */
    @GetMapping("/dict-types")
    @Timed
    public ResponseEntity<List<DictType>> getAllDictTypes(Pageable pageable) {
        log.debug("REST request to get a page of DictTypes");
        Page<DictType> page = dictTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dict-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dict-types/:id : get the "id" dictType.
     *
     * @param id the id of the dictType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dictType, or with status 404 (Not Found)
     */
    @GetMapping("/dict-types/{id}")
    @Timed
    public ResponseEntity<DictType> getDictType(@PathVariable Long id) {
        log.debug("REST request to get DictType : {}", id);
        Optional<DictType> dictType = dictTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dictType);
    }

    /**
     * DELETE  /dict-types/:id : delete the "id" dictType.
     *
     * @param id the id of the dictType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dict-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDictType(@PathVariable Long id) {
        log.debug("REST request to delete DictType : {}", id);

        dictTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
