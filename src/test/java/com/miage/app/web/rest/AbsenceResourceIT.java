package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Absence;
import com.miage.app.repository.AbsenceRepository;

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
 * Integration tests for the {@link AbsenceResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AbsenceResourceIT {

    private static final Integer DEFAULT_ID_ABSENCE = 1;
    private static final Integer UPDATED_ID_ABSENCE = 2;

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbsenceMockMvc;

    private Absence absence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Absence createEntity(EntityManager em) {
        Absence absence = new Absence()
            .idAbsence(DEFAULT_ID_ABSENCE);
        return absence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Absence createUpdatedEntity(EntityManager em) {
        Absence absence = new Absence()
            .idAbsence(UPDATED_ID_ABSENCE);
        return absence;
    }

    @BeforeEach
    public void initTest() {
        absence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbsence() throws Exception {
        int databaseSizeBeforeCreate = absenceRepository.findAll().size();
        // Create the Absence
        restAbsenceMockMvc.perform(post("/api/absences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isCreated());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeCreate + 1);
        Absence testAbsence = absenceList.get(absenceList.size() - 1);
        assertThat(testAbsence.getIdAbsence()).isEqualTo(DEFAULT_ID_ABSENCE);
    }

    @Test
    @Transactional
    public void createAbsenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = absenceRepository.findAll().size();

        // Create the Absence with an existing ID
        absence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbsenceMockMvc.perform(post("/api/absences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isBadRequest());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdAbsenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = absenceRepository.findAll().size();
        // set the field null
        absence.setIdAbsence(null);

        // Create the Absence, which fails.


        restAbsenceMockMvc.perform(post("/api/absences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isBadRequest());

        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAbsences() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get all the absenceList
        restAbsenceMockMvc.perform(get("/api/absences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(absence.getId().intValue())))
            .andExpect(jsonPath("$.[*].idAbsence").value(hasItem(DEFAULT_ID_ABSENCE)));
    }
    
    @Test
    @Transactional
    public void getAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        // Get the absence
        restAbsenceMockMvc.perform(get("/api/absences/{id}", absence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(absence.getId().intValue()))
            .andExpect(jsonPath("$.idAbsence").value(DEFAULT_ID_ABSENCE));
    }
    @Test
    @Transactional
    public void getNonExistingAbsence() throws Exception {
        // Get the absence
        restAbsenceMockMvc.perform(get("/api/absences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        int databaseSizeBeforeUpdate = absenceRepository.findAll().size();

        // Update the absence
        Absence updatedAbsence = absenceRepository.findById(absence.getId()).get();
        // Disconnect from session so that the updates on updatedAbsence are not directly saved in db
        em.detach(updatedAbsence);
        updatedAbsence
            .idAbsence(UPDATED_ID_ABSENCE);

        restAbsenceMockMvc.perform(put("/api/absences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbsence)))
            .andExpect(status().isOk());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeUpdate);
        Absence testAbsence = absenceList.get(absenceList.size() - 1);
        assertThat(testAbsence.getIdAbsence()).isEqualTo(UPDATED_ID_ABSENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingAbsence() throws Exception {
        int databaseSizeBeforeUpdate = absenceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbsenceMockMvc.perform(put("/api/absences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(absence)))
            .andExpect(status().isBadRequest());

        // Validate the Absence in the database
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbsence() throws Exception {
        // Initialize the database
        absenceRepository.saveAndFlush(absence);

        int databaseSizeBeforeDelete = absenceRepository.findAll().size();

        // Delete the absence
        restAbsenceMockMvc.perform(delete("/api/absences/{id}", absence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Absence> absenceList = absenceRepository.findAll();
        assertThat(absenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
