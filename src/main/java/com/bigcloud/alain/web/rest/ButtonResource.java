package com.bigcloud.alain.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bigcloud.alain.domain.Button;
import com.bigcloud.alain.repository.ButtonRepository;
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
 * REST controller for managing Button.
 */
@RestController
@RequestMapping("/api")
public class ButtonResource {

    private final Logger log = LoggerFactory.getLogger(ButtonResource.class);

    private static final String ENTITY_NAME = "button";

    private final ButtonRepository buttonRepository;

    public ButtonResource(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    /**
     * POST  /buttons : Create a new button.
     *
     * @param button the button to create
     * @return the ResponseEntity with status 201 (Created) and with body the new button, or with status 400 (Bad Request) if the button has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buttons")
    @Timed
    public ResponseEntity<Button> createButton(@RequestBody Button button) throws URISyntaxException {
        log.debug("REST request to save Button : {}", button);
        if (button.getId() != null) {
            throw new BadRequestAlertException("A new button cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Button result = buttonRepository.save(button);
        return ResponseEntity.created(new URI("/api/buttons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buttons : Updates an existing button.
     *
     * @param button the button to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated button,
     * or with status 400 (Bad Request) if the button is not valid,
     * or with status 500 (Internal Server Error) if the button couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buttons")
    @Timed
    public ResponseEntity<Button> updateButton(@RequestBody Button button) throws URISyntaxException {
        log.debug("REST request to update Button : {}", button);
        if (button.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Button result = buttonRepository.save(button);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, button.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buttons : get all the buttons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buttons in body
     */
    @GetMapping("/buttons")
    @Timed
    public List<Button> getAllButtons() {
        log.debug("REST request to get all Buttons");
        return buttonRepository.findAll();
    }

    /**
     * GET  /buttons/:id : get the "id" button.
     *
     * @param id the id of the button to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the button, or with status 404 (Not Found)
     */
    @GetMapping("/buttons/{id}")
    @Timed
    public ResponseEntity<Button> getButton(@PathVariable Long id) {
        log.debug("REST request to get Button : {}", id);
        Optional<Button> button = buttonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(button);
    }

    /**
     * DELETE  /buttons/:id : delete the "id" button.
     *
     * @param id the id of the button to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buttons/{id}")
    @Timed
    public ResponseEntity<Void> deleteButton(@PathVariable Long id) {
        log.debug("REST request to delete Button : {}", id);

        buttonRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
