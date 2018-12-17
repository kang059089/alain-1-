package com.bigcloud.alain.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.App;
import com.bigcloud.alain.repository.AppRepository;
import com.bigcloud.alain.web.rest.errors.BadRequestAlertException;
import com.bigcloud.alain.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing App.
 */
@RestController
@RequestMapping("/api")
public class AppResource {

    private final Logger log = LoggerFactory.getLogger(AppResource.class);

    private static final String ENTITY_NAME = "app";

    private final AppRepository appRepository;

    public AppResource(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    /**
     * POST  /apps : Create a new app.
     *
     * @param app the app to create
     * @return the ResponseEntity with status 201 (Created) and with body the new app, or with status 400 (Bad Request) if the app has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apps")
    @Timed
    public ResponseEntity<App> createApp(@RequestBody App app) throws URISyntaxException {
        log.debug("REST request to save App : {}", app);
        if (app.getId() != null) {
            throw new BadRequestAlertException("A new app cannot already have an ID", ENTITY_NAME, "idexists");
        }
        App result = appRepository.save(app);
        return ResponseEntity.created(new URI("/api/apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apps : Updates an existing app.
     *
     * @param app the app to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated app,
     * or with status 400 (Bad Request) if the app is not valid,
     * or with status 500 (Internal Server Error) if the app couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apps")
    @Timed
    public ResponseEntity<App> updateApp(@RequestBody App app) throws URISyntaxException {
        log.debug("REST request to update App : {}", app);
        if (app.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        App result = appRepository.save(app);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, app.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apps : get all the apps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apps in body
     */
    @GetMapping("/apps")
    @Timed
    public List<App> getAllApps() {
        log.debug("REST request to get all Apps");
        return appRepository.findAll();
    }

    /**
     * GET  /apps/:id : get the "id" app.
     *
     * @param id the id of the app to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the app, or with status 404 (Not Found)
     */
    @GetMapping("/apps/{id}")
    @Timed
    public ResponseEntity<App> getApp(@PathVariable Long id) {
        log.debug("REST request to get App : {}", id);
        Optional<App> app = appRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(app);
    }

    /**
     * DELETE  /apps/:id : delete the "id" app.
     *
     * @param id the id of the app to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apps/{id}")
    @Timed
    public ResponseEntity<Void> deleteApp(@PathVariable Long id) {
        log.debug("REST request to delete App : {}", id);

        appRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
