package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Horaire;
import com.miage.app.repository.HoraireRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HoraireResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HoraireResourceIT {

    private static final Integer DEFAULT_ID_HORAIRE = 1;
    private static final Integer UPDATED_ID_HORAIRE = 2;

    private static final String DEFAULT_HEURE_DEPART = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEPART = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_F = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_F = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HoraireRepository horaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoraireMockMvc;

    private Horaire horaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horaire createEntity(EntityManager em) {
        Horaire horaire = new Horaire()
            .idHoraire(DEFAULT_ID_HORAIRE)
            .heureDepart(DEFAULT_HEURE_DEPART)
            .heureF(DEFAULT_HEURE_F)
            .date(DEFAULT_DATE);
        return horaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horaire createUpdatedEntity(EntityManager em) {
        Horaire horaire = new Horaire()
            .idHoraire(UPDATED_ID_HORAIRE)
            .heureDepart(UPDATED_HEURE_DEPART)
            .heureF(UPDATED_HEURE_F)
            .date(UPDATED_DATE);
        return horaire;
    }

    @BeforeEach
    public void initTest() {
        horaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoraire() throws Exception {
        int databaseSizeBeforeCreate = horaireRepository.findAll().size();
        // Create the Horaire
        restHoraireMockMvc.perform(post("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isCreated());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeCreate + 1);
        Horaire testHoraire = horaireList.get(horaireList.size() - 1);
        assertThat(testHoraire.getIdHoraire()).isEqualTo(DEFAULT_ID_HORAIRE);
        assertThat(testHoraire.getHeureDepart()).isEqualTo(DEFAULT_HEURE_DEPART);
        assertThat(testHoraire.getHeureF()).isEqualTo(DEFAULT_HEURE_F);
        assertThat(testHoraire.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createHoraireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horaireRepository.findAll().size();

        // Create the Horaire with an existing ID
        horaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoraireMockMvc.perform(post("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isBadRequest());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdHoraireIsRequired() throws Exception {
        int databaseSizeBeforeTest = horaireRepository.findAll().size();
        // set the field null
        horaire.setIdHoraire(null);

        // Create the Horaire, which fails.


        restHoraireMockMvc.perform(post("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isBadRequest());

        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeureDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = horaireRepository.findAll().size();
        // set the field null
        horaire.setHeureDepart(null);

        // Create the Horaire, which fails.


        restHoraireMockMvc.perform(post("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isBadRequest());

        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeureFIsRequired() throws Exception {
        int databaseSizeBeforeTest = horaireRepository.findAll().size();
        // set the field null
        horaire.setHeureF(null);

        // Create the Horaire, which fails.


        restHoraireMockMvc.perform(post("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isBadRequest());

        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = horaireRepository.findAll().size();
        // set the field null
        horaire.setDate(null);

        // Create the Horaire, which fails.


        restHoraireMockMvc.perform(post("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isBadRequest());

        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHoraires() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaireList
        restHoraireMockMvc.perform(get("/api/horaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHoraire").value(hasItem(DEFAULT_ID_HORAIRE)))
            .andExpect(jsonPath("$.[*].heureDepart").value(hasItem(DEFAULT_HEURE_DEPART)))
            .andExpect(jsonPath("$.[*].heureF").value(hasItem(DEFAULT_HEURE_F)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get the horaire
        restHoraireMockMvc.perform(get("/api/horaires/{id}", horaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horaire.getId().intValue()))
            .andExpect(jsonPath("$.idHoraire").value(DEFAULT_ID_HORAIRE))
            .andExpect(jsonPath("$.heureDepart").value(DEFAULT_HEURE_DEPART))
            .andExpect(jsonPath("$.heureF").value(DEFAULT_HEURE_F))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingHoraire() throws Exception {
        // Get the horaire
        restHoraireMockMvc.perform(get("/api/horaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        int databaseSizeBeforeUpdate = horaireRepository.findAll().size();

        // Update the horaire
        Horaire updatedHoraire = horaireRepository.findById(horaire.getId()).get();
        // Disconnect from session so that the updates on updatedHoraire are not directly saved in db
        em.detach(updatedHoraire);
        updatedHoraire
            .idHoraire(UPDATED_ID_HORAIRE)
            .heureDepart(UPDATED_HEURE_DEPART)
            .heureF(UPDATED_HEURE_F)
            .date(UPDATED_DATE);

        restHoraireMockMvc.perform(put("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHoraire)))
            .andExpect(status().isOk());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeUpdate);
        Horaire testHoraire = horaireList.get(horaireList.size() - 1);
        assertThat(testHoraire.getIdHoraire()).isEqualTo(UPDATED_ID_HORAIRE);
        assertThat(testHoraire.getHeureDepart()).isEqualTo(UPDATED_HEURE_DEPART);
        assertThat(testHoraire.getHeureF()).isEqualTo(UPDATED_HEURE_F);
        assertThat(testHoraire.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHoraire() throws Exception {
        int databaseSizeBeforeUpdate = horaireRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoraireMockMvc.perform(put("/api/horaires")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(horaire)))
            .andExpect(status().isBadRequest());

        // Validate the Horaire in the database
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        int databaseSizeBeforeDelete = horaireRepository.findAll().size();

        // Delete the horaire
        restHoraireMockMvc.perform(delete("/api/horaires/{id}", horaire.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Horaire> horaireList = horaireRepository.findAll();
        assertThat(horaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
