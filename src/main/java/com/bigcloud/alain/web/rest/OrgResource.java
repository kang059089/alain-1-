package com.bigcloud.alain.web.rest;

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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Org.
 */
@RestController
@RequestMapping("/api")
public class OrgResource {

    private final Logger log = LoggerFactory.getLogger(OrgResource.class);

    private static final String ENTITY_NAME = "org";

    private final OrgRepository orgRepository;

    public OrgResource(OrgRepository orgRepository) {
        this.orgRepository = orgRepository;
    }

    /**
     * POST  /orgs : Create a new org.
     *
     * @param org the org to create
     * @return the ResponseEntity with status 201 (Created) and with body the new org, or with status 400 (Bad Request) if the org has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orgs")
    @Timed
    public ResponseEntity<Org> createOrg(@RequestBody Org org) throws URISyntaxException {
        log.debug("REST request to save Org : {}", org);
        if (org.getId() != null) {
            throw new BadRequestAlertException("A new org cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Org result = orgRepository.save(org);
        return ResponseEntity.created(new URI("/api/orgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orgs : Updates an existing org.
     *
     * @param org the org to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated org,
     * or with status 400 (Bad Request) if the org is not valid,
     * or with status 500 (Internal Server Error) if the org couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orgs")
    @Timed
    public ResponseEntity<Org> updateOrg(@RequestBody Org org) throws URISyntaxException {
        log.debug("REST request to update Org : {}", org);
        if (org.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
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
}
