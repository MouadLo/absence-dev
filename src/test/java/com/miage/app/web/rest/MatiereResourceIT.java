package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Matiere;
import com.miage.app.repository.MatiereRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MatiereResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MatiereResourceIT {

    private static final Integer DEFAULT_ID_MATIERE = 1;
    private static final Integer UPDATED_ID_MATIERE = 2;

    private static final String DEFAULT_NOM_MATIERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MATIERE = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATION = "BBBBBBBBBB";

    @Autowired
    private MatiereRepository matiereRepository;

    @Mock
    private MatiereRepository matiereRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatiereMockMvc;

    private Matiere matiere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .idMatiere(DEFAULT_ID_MATIERE)
            .nomMatiere(DEFAULT_NOM_MATIERE)
            .abreviation(DEFAULT_ABREVIATION);
        return matiere;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createUpdatedEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .idMatiere(UPDATED_ID_MATIERE)
            .nomMatiere(UPDATED_NOM_MATIERE)
            .abreviation(UPDATED_ABREVIATION);
        return matiere;
    }

    @BeforeEach
    public void initTest() {
        matiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiere() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();
        // Create the Matiere
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate + 1);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getIdMatiere()).isEqualTo(DEFAULT_ID_MATIERE);
        assertThat(testMatiere.getNomMatiere()).isEqualTo(DEFAULT_NOM_MATIERE);
        assertThat(testMatiere.getAbreviation()).isEqualTo(DEFAULT_ABREVIATION);
    }

    @Test
    @Transactional
    public void createMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere with an existing ID
        matiere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdMatiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereRepository.findAll().size();
        // set the field null
        matiere.setIdMatiere(null);

        // Create the Matiere, which fails.


        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomMatiereIsRequired() throws Exception {
        int databaseSizeBeforeTest = matiereRepository.findAll().size();
        // set the field null
        matiere.setNomMatiere(null);

        // Create the Matiere, which fails.


        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatieres() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMatiere").value(hasItem(DEFAULT_ID_MATIERE)))
            .andExpect(jsonPath("$.[*].nomMatiere").value(hasItem(DEFAULT_NOM_MATIERE)))
            .andExpect(jsonPath("$.[*].abreviation").value(hasItem(DEFAULT_ABREVIATION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMatieresWithEagerRelationshipsIsEnabled() throws Exception {
        when(matiereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatiereMockMvc.perform(get("/api/matieres?eagerload=true"))
            .andExpect(status().isOk());

        verify(matiereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMatieresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(matiereRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatiereMockMvc.perform(get("/api/matieres?eagerload=true"))
            .andExpect(status().isOk());

        verify(matiereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", matiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matiere.getId().intValue()))
            .andExpect(jsonPath("$.idMatiere").value(DEFAULT_ID_MATIERE))
            .andExpect(jsonPath("$.nomMatiere").value(DEFAULT_NOM_MATIERE))
            .andExpect(jsonPath("$.abreviation").value(DEFAULT_ABREVIATION));
    }
    @Test
    @Transactional
    public void getNonExistingMatiere() throws Exception {
        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Update the matiere
        Matiere updatedMatiere = matiereRepository.findById(matiere.getId()).get();
        // Disconnect from session so that the updates on updatedMatiere are not directly saved in db
        em.detach(updatedMatiere);
        updatedMatiere
            .idMatiere(UPDATED_ID_MATIERE)
            .nomMatiere(UPDATED_NOM_MATIERE)
            .abreviation(UPDATED_ABREVIATION);

        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatiere)))
            .andExpect(status().isOk());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getIdMatiere()).isEqualTo(UPDATED_ID_MATIERE);
        assertThat(testMatiere.getNomMatiere()).isEqualTo(UPDATED_NOM_MATIERE);
        assertThat(testMatiere.getAbreviation()).isEqualTo(UPDATED_ABREVIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiere() throws Exception {
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiere)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeDelete = matiereRepository.findAll().size();

        // Delete the matiere
        restMatiereMockMvc.perform(delete("/api/matieres/{id}", matiere.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
