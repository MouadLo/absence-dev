package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Classe;
import com.miage.app.repository.ClasseRepository;

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
 * Integration tests for the {@link ClasseResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClasseResourceIT {

    private static final Integer DEFAULT_ID_CLASSE = 1;
    private static final Integer UPDATED_ID_CLASSE = 2;

    private static final String DEFAULT_NOM_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU = "BBBBBBBBBB";

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasseMockMvc;

    private Classe classe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createEntity(EntityManager em) {
        Classe classe = new Classe()
            .idClasse(DEFAULT_ID_CLASSE)
            .nomClasse(DEFAULT_NOM_CLASSE)
            .niveau(DEFAULT_NIVEAU);
        return classe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createUpdatedEntity(EntityManager em) {
        Classe classe = new Classe()
            .idClasse(UPDATED_ID_CLASSE)
            .nomClasse(UPDATED_NOM_CLASSE)
            .niveau(UPDATED_NIVEAU);
        return classe;
    }

    @BeforeEach
    public void initTest() {
        classe = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasse() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();
        // Create the Classe
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classe)))
            .andExpect(status().isCreated());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate + 1);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getIdClasse()).isEqualTo(DEFAULT_ID_CLASSE);
        assertThat(testClasse.getNomClasse()).isEqualTo(DEFAULT_NOM_CLASSE);
        assertThat(testClasse.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
    }

    @Test
    @Transactional
    public void createClasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();

        // Create the Classe with an existing ID
        classe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classe)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeRepository.findAll().size();
        // set the field null
        classe.setIdClasse(null);

        // Create the Classe, which fails.


        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classe)))
            .andExpect(status().isBadRequest());

        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeRepository.findAll().size();
        // set the field null
        classe.setNomClasse(null);

        // Create the Classe, which fails.


        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classe)))
            .andExpect(status().isBadRequest());

        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNiveauIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeRepository.findAll().size();
        // set the field null
        classe.setNiveau(null);

        // Create the Classe, which fails.


        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classe)))
            .andExpect(status().isBadRequest());

        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasses() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].idClasse").value(hasItem(DEFAULT_ID_CLASSE)))
            .andExpect(jsonPath("$.[*].nomClasse").value(hasItem(DEFAULT_NOM_CLASSE)))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)));
    }
    
    @Test
    @Transactional
    public void getClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", classe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classe.getId().intValue()))
            .andExpect(jsonPath("$.idClasse").value(DEFAULT_ID_CLASSE))
            .andExpect(jsonPath("$.nomClasse").value(DEFAULT_NOM_CLASSE))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU));
    }
    @Test
    @Transactional
    public void getNonExistingClasse() throws Exception {
        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Update the classe
        Classe updatedClasse = classeRepository.findById(classe.getId()).get();
        // Disconnect from session so that the updates on updatedClasse are not directly saved in db
        em.detach(updatedClasse);
        updatedClasse
            .idClasse(UPDATED_ID_CLASSE)
            .nomClasse(UPDATED_NOM_CLASSE)
            .niveau(UPDATED_NIVEAU);

        restClasseMockMvc.perform(put("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClasse)))
            .andExpect(status().isOk());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getIdClasse()).isEqualTo(UPDATED_ID_CLASSE);
        assertThat(testClasse.getNomClasse()).isEqualTo(UPDATED_NOM_CLASSE);
        assertThat(testClasse.getNiveau()).isEqualTo(UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void updateNonExistingClasse() throws Exception {
        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseMockMvc.perform(put("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classe)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeDelete = classeRepository.findAll().size();

        // Delete the classe
        restClasseMockMvc.perform(delete("/api/classes/{id}", classe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
