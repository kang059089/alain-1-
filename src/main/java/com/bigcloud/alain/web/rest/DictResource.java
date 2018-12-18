package com.bigcloud.alain.web.rest;

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

import java.util.List;
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

    public DictResource(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    /**
     * POST  /dicts : Create a new dict.
     *
     * @param dict the dict to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dict, or with status 400 (Bad Request) if the dict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dicts")
    @Timed
    public ResponseEntity<Dict> createDict(@RequestBody Dict dict) throws URISyntaxException {
        log.debug("REST request to save Dict : {}", dict);
        if (dict.getId() != null) {
            throw new BadRequestAlertException("A new dict cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dict result = dictRepository.save(dict);
        return ResponseEntity.created(new URI("/api/dicts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dicts : Updates an existing dict.
     *
     * @param dict the dict to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dict,
     * or with status 400 (Bad Request) if the dict is not valid,
     * or with status 500 (Internal Server Error) if the dict couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dicts")
    @Timed
    public ResponseEntity<Dict> updateDict(@RequestBody Dict dict) throws URISyntaxException {
        log.debug("REST request to update Dict : {}", dict);
        if (dict.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
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
}
