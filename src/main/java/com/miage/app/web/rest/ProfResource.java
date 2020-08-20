package com.miage.app.web.rest;

import com.miage.app.domain.Prof;
import com.miage.app.repository.ProfRepository;
import com.miage.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.miage.app.domain.Prof}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfResource {

    private final Logger log = LoggerFactory.getLogger(ProfResource.class);

    private static final String ENTITY_NAME = "prof";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfRepository profRepository;

    public ProfResource(ProfRepository profRepository) {
        this.profRepository = profRepository;
    }

    /**
     * {@code POST  /profs} : Create a new prof.
     *
     * @param prof the prof to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prof, or with status {@code 400 (Bad Request)} if the prof has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profs")
    public ResponseEntity<Prof> createProf(@Valid @RequestBody Prof prof) throws URISyntaxException {
        log.debug("REST request to save Prof : {}", prof);
        if (prof.getId() != null) {
            throw new BadRequestAlertException("A new prof cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prof result = profRepository.save(prof);
        return ResponseEntity.created(new URI("/api/profs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profs} : Updates an existing prof.
     *
     * @param prof the prof to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prof,
     * or with status {@code 400 (Bad Request)} if the prof is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prof couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profs")
    public ResponseEntity<Prof> updateProf(@Valid @RequestBody Prof prof) throws URISyntaxException {
        log.debug("REST request to update Prof : {}", prof);
        if (prof.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prof result = profRepository.save(prof);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prof.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profs} : get all the profs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profs in body.
     */
    @GetMapping("/profs")
    public List<Prof> getAllProfs() {
        log.debug("REST request to get all Profs");
        return profRepository.findAll();
    }

    /**
     * {@code GET  /profs/:id} : get the "id" prof.
     *
     * @param id the id of the prof to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prof, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profs/{id}")
    public ResponseEntity<Prof> getProf(@PathVariable Long id) {
        log.debug("REST request to get Prof : {}", id);
        Optional<Prof> prof = profRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prof);
    }

    /**
     * {@code DELETE  /profs/:id} : delete the "id" prof.
     *
     * @param id the id of the prof to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profs/{id}")
    public ResponseEntity<Void> deleteProf(@PathVariable Long id) {
        log.debug("REST request to delete Prof : {}", id);
        profRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
