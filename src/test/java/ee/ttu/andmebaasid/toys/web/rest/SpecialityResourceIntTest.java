package ee.ttu.andmebaasid.toys.web.rest;

import ee.ttu.andmebaasid.toys.ToysApp;

import ee.ttu.andmebaasid.toys.domain.Speciality;
import ee.ttu.andmebaasid.toys.repository.SpecialityRepository;
import ee.ttu.andmebaasid.toys.service.SpecialityService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpecialityResource REST controller.
 *
 * @see SpecialityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToysApp.class)
public class SpecialityResourceIntTest {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private SpecialityRepository specialityRepository;

    @Inject
    private SpecialityService specialityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSpecialityMockMvc;

    private Speciality speciality;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpecialityResource specialityResource = new SpecialityResource();
        ReflectionTestUtils.setField(specialityResource, "specialityService", specialityService);
        this.restSpecialityMockMvc = MockMvcBuilders.standaloneSetup(specialityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speciality createEntity(EntityManager em) {
        Speciality speciality = new Speciality()
                .code(DEFAULT_CODE)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return speciality;
    }

    @Before
    public void initTest() {
        speciality = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeciality() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // Create the Speciality

        restSpecialityMockMvc.perform(post("/api/specialities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(speciality)))
                .andExpect(status().isCreated());

        // Validate the Speciality in the database
        List<Speciality> specialities = specialityRepository.findAll();
        assertThat(specialities).hasSize(databaseSizeBeforeCreate + 1);
        Speciality testSpeciality = specialities.get(specialities.size() - 1);
        assertThat(testSpeciality.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSpeciality.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpeciality.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialityRepository.findAll().size();
        // set the field null
        speciality.setCode(null);

        // Create the Speciality, which fails.

        restSpecialityMockMvc.perform(post("/api/specialities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(speciality)))
                .andExpect(status().isBadRequest());

        List<Speciality> specialities = specialityRepository.findAll();
        assertThat(specialities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialityRepository.findAll().size();
        // set the field null
        speciality.setName(null);

        // Create the Speciality, which fails.

        restSpecialityMockMvc.perform(post("/api/specialities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(speciality)))
                .andExpect(status().isBadRequest());

        List<Speciality> specialities = specialityRepository.findAll();
        assertThat(specialities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialities() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);

        // Get all the specialities
        restSpecialityMockMvc.perform(get("/api/specialities?sort=code,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.saveAndFlush(speciality);

        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{code}", speciality.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpeciality() throws Exception {
        // Get the speciality
        restSpecialityMockMvc.perform(get("/api/specialities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeciality() throws Exception {
        // Initialize the database
        specialityService.save(speciality);

        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality
        Speciality updatedSpeciality = specialityRepository.findOneByCode(speciality.getCode());
        updatedSpeciality
                .code(UPDATED_CODE)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);

        restSpecialityMockMvc.perform(put("/api/specialities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSpeciality)))
                .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialities = specialityRepository.findAll();
        assertThat(specialities).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialities.get(specialities.size() - 1);
        assertThat(testSpeciality.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSpeciality.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpeciality.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSpeciality() throws Exception {
        // Initialize the database
        specialityService.save(speciality);

        int databaseSizeBeforeDelete = specialityRepository.findAll().size();

        // Get the speciality
        restSpecialityMockMvc.perform(delete("/api/specialities/{code}", speciality.getCode())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Speciality> specialities = specialityRepository.findAll();
        assertThat(specialities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
