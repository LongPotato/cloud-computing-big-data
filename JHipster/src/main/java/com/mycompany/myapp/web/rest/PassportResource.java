package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Passport;

import com.mycompany.myapp.repository.PassportRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing Passport.
 */
@RestController
@RequestMapping("/api")
public class PassportResource {

    private final Logger log = LoggerFactory.getLogger(PassportResource.class);

    private static final String ENTITY_NAME = "passport";
        
    private final PassportRepository passportRepository;

    public PassportResource(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    /**
     * POST  /passports : Create a new passport.
     *
     * @param passport the passport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new passport, or with status 400 (Bad Request) if the passport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/passports")
    @Timed
    public ResponseEntity<Passport> createPassport(@RequestBody Passport passport) throws URISyntaxException {
        log.debug("REST request to save Passport : {}", passport);
        if (passport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new passport cannot already have an ID")).body(null);
        }
        Passport result = passportRepository.save(passport);
        return ResponseEntity.created(new URI("/api/passports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /passports : Updates an existing passport.
     *
     * @param passport the passport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated passport,
     * or with status 400 (Bad Request) if the passport is not valid,
     * or with status 500 (Internal Server Error) if the passport couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/passports")
    @Timed
    public ResponseEntity<Passport> updatePassport(@RequestBody Passport passport) throws URISyntaxException {
        log.debug("REST request to update Passport : {}", passport);
        if (passport.getId() == null) {
            return createPassport(passport);
        }
        Passport result = passportRepository.save(passport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, passport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /passports : get all the passports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of passports in body
     */
    @GetMapping("/passports")
    @Timed
    public List<Passport> getAllPassports() {
        log.debug("REST request to get all Passports");
        List<Passport> passports = passportRepository.findAll();
        return passports;
    }

    /**
     * GET  /passports/:id : get the "id" passport.
     *
     * @param id the id of the passport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the passport, or with status 404 (Not Found)
     */
    @GetMapping("/passports/{id}")
    @Timed
    public ResponseEntity<Passport> getPassport(@PathVariable Long id) {
        log.debug("REST request to get Passport : {}", id);
        Passport passport = passportRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(passport));
    }

    /**
     * DELETE  /passports/:id : delete the "id" passport.
     *
     * @param id the id of the passport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/passports/{id}")
    @Timed
    public ResponseEntity<Void> deletePassport(@PathVariable Long id) {
        log.debug("REST request to delete Passport : {}", id);
        passportRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
