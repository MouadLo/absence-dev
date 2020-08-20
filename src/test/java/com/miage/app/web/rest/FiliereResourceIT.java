package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Filiere;
import com.miage.app.repository.FiliereRepository;

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
 * Integration tests for the {@link FiliereResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FiliereResourceIT {

    private static final Integer DEFAULT_ID_FILIERE = 1;
    private static final Integer UPDATED_ID_FILIERE = 2;

    private static final String DEFAULT_NOM_FILIERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FILIERE = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATION = "BBBBBBBBBB";

    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFiliereMockMvc;

    private Filiere filiere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filiere createEntity(EntityManager em) {
        Filiere filiere = new Filiere()
            .idFiliere(DEFAULT_ID_FILIERE)
            .nomFiliere(DEFAULT_NOM_FILIERE)
            .abreviation(DEFAULT_ABREVIATION);
        return filiere;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filiere createUpdatedEntity(EntityManager em) {
        Filiere filiere = new Filiere()
            .idFiliere(UPDATED_ID_FILIERE)
            .nomFiliere(UPDATED_NOM_FILIERE)
            .abreviation(UPDATED_ABREVIATION);
        return filiere;
    }

    @BeforeEach
    public void initTest() {
        filiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiliere() throws Exception {
        int databaseSizeBeforeCreate = filiereRepository.findAll().size();
        // Create the Filiere
        restFiliereMockMvc.perform(post("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isCreated());

        // Validate the Filiere in the database
        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeCreate + 1);
        Filiere testFiliere = filiereList.get(filiereList.size() - 1);
        assertThat(testFiliere.getIdFiliere()).isEqualTo(DEFAULT_ID_FILIERE);
        assertThat(testFiliere.getNomFiliere()).isEqualTo(DEFAULT_NOM_FILIERE);
        assertThat(testFiliere.getAbreviation()).isEqualTo(DEFAULT_ABREVIATION);
    }

    @Test
    @Transactional
    public void createFiliereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filiereRepository.findAll().size();

        // Create the Filiere with an existing ID
        filiere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFiliereMockMvc.perform(post("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isBadRequest());

        // Validate the Filiere in the database
        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdFiliereIsRequired() throws Exception {
        int databaseSizeBeforeTest = filiereRepository.findAll().size();
        // set the field null
        filiere.setIdFiliere(null);

        // Create the Filiere, which fails.


        restFiliereMockMvc.perform(post("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isBadRequest());

        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomFiliereIsRequired() throws Exception {
        int databaseSizeBeforeTest = filiereRepository.findAll().size();
        // set the field null
        filiere.setNomFiliere(null);

        // Create the Filiere, which fails.


        restFiliereMockMvc.perform(post("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isBadRequest());

        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbreviationIsRequired() throws Exception {
        int databaseSizeBeforeTest = filiereRepository.findAll().size();
        // set the field null
        filiere.setAbreviation(null);

        // Create the Filiere, which fails.


        restFiliereMockMvc.perform(post("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isBadRequest());

        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilieres() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        // Get all the filiereList
        restFiliereMockMvc.perform(get("/api/filieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFiliere").value(hasItem(DEFAULT_ID_FILIERE)))
            .andExpect(jsonPath("$.[*].nomFiliere").value(hasItem(DEFAULT_NOM_FILIERE)))
            .andExpect(jsonPath("$.[*].abreviation").value(hasItem(DEFAULT_ABREVIATION)));
    }
    
    @Test
    @Transactional
    public void getFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        // Get the filiere
        restFiliereMockMvc.perform(get("/api/filieres/{id}", filiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filiere.getId().intValue()))
            .andExpect(jsonPath("$.idFiliere").value(DEFAULT_ID_FILIERE))
            .andExpect(jsonPath("$.nomFiliere").value(DEFAULT_NOM_FILIERE))
            .andExpect(jsonPath("$.abreviation").value(DEFAULT_ABREVIATION));
    }
    @Test
    @Transactional
    public void getNonExistingFiliere() throws Exception {
        // Get the filiere
        restFiliereMockMvc.perform(get("/api/filieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        int databaseSizeBeforeUpdate = filiereRepository.findAll().size();

        // Update the filiere
        Filiere updatedFiliere = filiereRepository.findById(filiere.getId()).get();
        // Disconnect from session so that the updates on updatedFiliere are not directly saved in db
        em.detach(updatedFiliere);
        updatedFiliere
            .idFiliere(UPDATED_ID_FILIERE)
            .nomFiliere(UPDATED_NOM_FILIERE)
            .abreviation(UPDATED_ABREVIATION);

        restFiliereMockMvc.perform(put("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFiliere)))
            .andExpect(status().isOk());

        // Validate the Filiere in the database
        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeUpdate);
        Filiere testFiliere = filiereList.get(filiereList.size() - 1);
        assertThat(testFiliere.getIdFiliere()).isEqualTo(UPDATED_ID_FILIERE);
        assertThat(testFiliere.getNomFiliere()).isEqualTo(UPDATED_NOM_FILIERE);
        assertThat(testFiliere.getAbreviation()).isEqualTo(UPDATED_ABREVIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingFiliere() throws Exception {
        int databaseSizeBeforeUpdate = filiereRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFiliereMockMvc.perform(put("/api/filieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isBadRequest());

        // Validate the Filiere in the database
        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        int databaseSizeBeforeDelete = filiereRepository.findAll().size();

        // Delete the filiere
        restFiliereMockMvc.perform(delete("/api/filieres/{id}", filiere.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Filiere> filiereList = filiereRepository.findAll();
        assertThat(filiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
