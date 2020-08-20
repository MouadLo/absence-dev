package com.miage.app.web.rest;

import com.miage.app.IntranetApp;
import com.miage.app.domain.Admin;
import com.miage.app.repository.AdminRepository;

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
 * Integration tests for the {@link AdminResource} REST controller.
 */
@SpringBootTest(classes = IntranetApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdminResourceIT {

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

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminMockMvc;

    private Admin admin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createEntity(EntityManager em) {
        Admin admin = new Admin()
            .login(DEFAULT_LOGIN)
            .mdp(DEFAULT_MDP)
            .email(DEFAULT_EMAIL)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .tel(DEFAULT_TEL);
        return admin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createUpdatedEntity(EntityManager em) {
        Admin admin = new Admin()
            .login(UPDATED_LOGIN)
            .mdp(UPDATED_MDP)
            .email(UPDATED_EMAIL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .tel(UPDATED_TEL);
        return admin;
    }

    @BeforeEach
    public void initTest() {
        admin = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdmin() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();
        // Create the Admin
        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isCreated());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate + 1);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testAdmin.getMdp()).isEqualTo(DEFAULT_MDP);
        assertThat(testAdmin.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAdmin.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAdmin.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAdmin.getTel()).isEqualTo(DEFAULT_TEL);
    }

    @Test
    @Transactional
    public void createAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();

        // Create the Admin with an existing ID
        admin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setLogin(null);

        // Create the Admin, which fails.


        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMdpIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setMdp(null);

        // Create the Admin, which fails.


        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setEmail(null);

        // Create the Admin, which fails.


        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setNom(null);

        // Create the Admin, which fails.


        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setPrenom(null);

        // Create the Admin, which fails.


        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setTel(null);

        // Create the Admin, which fails.


        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdmins() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admin.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].mdp").value(hasItem(DEFAULT_MDP)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)));
    }
    
    @Test
    @Transactional
    public void getAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", admin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(admin.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.mdp").value(DEFAULT_MDP))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL));
    }
    @Test
    @Transactional
    public void getNonExistingAdmin() throws Exception {
        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // Update the admin
        Admin updatedAdmin = adminRepository.findById(admin.getId()).get();
        // Disconnect from session so that the updates on updatedAdmin are not directly saved in db
        em.detach(updatedAdmin);
        updatedAdmin
            .login(UPDATED_LOGIN)
            .mdp(UPDATED_MDP)
            .email(UPDATED_EMAIL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .tel(UPDATED_TEL);

        restAdminMockMvc.perform(put("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdmin)))
            .andExpect(status().isOk());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testAdmin.getMdp()).isEqualTo(UPDATED_MDP);
        assertThat(testAdmin.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAdmin.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAdmin.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAdmin.getTel()).isEqualTo(UPDATED_TEL);
    }

    @Test
    @Transactional
    public void updateNonExistingAdmin() throws Exception {
        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminMockMvc.perform(put("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        int databaseSizeBeforeDelete = adminRepository.findAll().size();

        // Delete the admin
        restAdminMockMvc.perform(delete("/api/admins/{id}", admin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
