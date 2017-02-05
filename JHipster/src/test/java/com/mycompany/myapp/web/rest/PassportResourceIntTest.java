package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JHipsterApp;

import com.mycompany.myapp.domain.Passport;
import com.mycompany.myapp.repository.PassportRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PassportResource REST controller.
 *
 * @see PassportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterApp.class)
public class PassportResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPassportMockMvc;

    private Passport passport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            PassportResource passportResource = new PassportResource(passportRepository);
        this.restPassportMockMvc = MockMvcBuilders.standaloneSetup(passportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passport createEntity(EntityManager em) {
        Passport passport = new Passport()
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE);
        return passport;
    }

    @Before
    public void initTest() {
        passport = createEntity(em);
    }

    @Test
    @Transactional
    public void createPassport() throws Exception {
        int databaseSizeBeforeCreate = passportRepository.findAll().size();

        // Create the Passport

        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passport)))
            .andExpect(status().isCreated());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate + 1);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPassport.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createPassportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passportRepository.findAll().size();

        // Create the Passport with an existing ID
        Passport existingPassport = new Passport();
        existingPassport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassportMockMvc.perform(post("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPassport)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPassports() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get all the passportList
        restPassportMockMvc.perform(get("/api/passports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passport.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    @Test
    @Transactional
    public void getPassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);

        // Get the passport
        restPassportMockMvc.perform(get("/api/passports/{id}", passport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(passport.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingPassport() throws Exception {
        // Get the passport
        restPassportMockMvc.perform(get("/api/passports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Update the passport
        Passport updatedPassport = passportRepository.findOne(passport.getId());
        updatedPassport
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE);

        restPassportMockMvc.perform(put("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPassport)))
            .andExpect(status().isOk());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate);
        Passport testPassport = passportList.get(passportList.size() - 1);
        assertThat(testPassport.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPassport.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPassport() throws Exception {
        int databaseSizeBeforeUpdate = passportRepository.findAll().size();

        // Create the Passport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPassportMockMvc.perform(put("/api/passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(passport)))
            .andExpect(status().isCreated());

        // Validate the Passport in the database
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePassport() throws Exception {
        // Initialize the database
        passportRepository.saveAndFlush(passport);
        int databaseSizeBeforeDelete = passportRepository.findAll().size();

        // Get the passport
        restPassportMockMvc.perform(delete("/api/passports/{id}", passport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Passport> passportList = passportRepository.findAll();
        assertThat(passportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passport.class);
    }
}
