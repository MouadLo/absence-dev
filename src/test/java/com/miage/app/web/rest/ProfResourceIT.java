package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Prof;
import com.miage.app.repository.ProfRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProfResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_MDP = "AAAAAAAAAA";
    private static final String UPDATED_MDP = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Autowired
    private ProfRepository profRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfMockMvc;

    private Prof prof;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prof createEntity(EntityManager em) {
        Prof prof = new Prof()
            .login(DEFAULT_LOGIN)
            .mdp(DEFAULT_MDP)
            .email(DEFAULT_EMAIL)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .type(DEFAULT_TYPE)
            .tel(DEFAULT_TEL);
        return prof;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prof createUpdatedEntity(EntityManager em) {
        Prof prof = new Prof()
            .login(UPDATED_LOGIN)
            .mdp(UPDATED_MDP)
            .email(UPDATED_EMAIL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .type(UPDATED_TYPE)
            .tel(UPDATED_TEL);
        return prof;
    }

    @BeforeEach
    public void initTest() {
        prof = createEntity(em);
    }

    @Test
    @Transactional
    public void createProf() throws Exception {
        int databaseSizeBeforeCreate = profRepository.findAll().size();
        // Create the Prof
        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isCreated());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeCreate + 1);
        Prof testProf = profList.get(profList.size() - 1);
        assertThat(testProf.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testProf.getMdp()).isEqualTo(DEFAULT_MDP);
        assertThat(testProf.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProf.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProf.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testProf.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProf.getTel()).isEqualTo(DEFAULT_TEL);
    }

    @Test
    @Transactional
    public void createProfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profRepository.findAll().size();

        // Create the Prof with an existing ID
        prof.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setLogin(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMdpIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setMdp(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setEmail(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setNom(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setPrenom(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setType(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelIsRequired() throws Exception {
        int databaseSizeBeforeTest = profRepository.findAll().size();
        // set the field null
        prof.setTel(null);

        // Create the Prof, which fails.


        restProfMockMvc.perform(post("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfs() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        // Get all the profList
        restProfMockMvc.perform(get("/api/profs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prof.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].mdp").value(hasItem(DEFAULT_MDP)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)));
    }
    
    @Test
    @Transactional
    public void getProf() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        // Get the prof
        restProfMockMvc.perform(get("/api/profs/{id}", prof.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prof.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.mdp").value(DEFAULT_MDP))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL));
    }
    @Test
    @Transactional
    public void getNonExistingProf() throws Exception {
        // Get the prof
        restProfMockMvc.perform(get("/api/profs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProf() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        int databaseSizeBeforeUpdate = profRepository.findAll().size();

        // Update the prof
        Prof updatedProf = profRepository.findById(prof.getId()).get();
        // Disconnect from session so that the updates on updatedProf are not directly saved in db
        em.detach(updatedProf);
        updatedProf
            .login(UPDATED_LOGIN)
            .mdp(UPDATED_MDP)
            .email(UPDATED_EMAIL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .type(UPDATED_TYPE)
            .tel(UPDATED_TEL);

        restProfMockMvc.perform(put("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProf)))
            .andExpect(status().isOk());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeUpdate);
        Prof testProf = profList.get(profList.size() - 1);
        assertThat(testProf.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testProf.getMdp()).isEqualTo(UPDATED_MDP);
        assertThat(testProf.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProf.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProf.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testProf.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProf.getTel()).isEqualTo(UPDATED_TEL);
    }

    @Test
    @Transactional
    public void updateNonExistingProf() throws Exception {
        int databaseSizeBeforeUpdate = profRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfMockMvc.perform(put("/api/profs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prof)))
            .andExpect(status().isBadRequest());

        // Validate the Prof in the database
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProf() throws Exception {
        // Initialize the database
        profRepository.saveAndFlush(prof);

        int databaseSizeBeforeDelete = profRepository.findAll().size();

        // Delete the prof
        restProfMockMvc.perform(delete("/api/profs/{id}", prof.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prof> profList = profRepository.findAll();
        assertThat(profList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
