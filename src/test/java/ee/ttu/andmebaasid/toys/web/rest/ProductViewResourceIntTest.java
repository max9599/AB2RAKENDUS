package ee.ttu.andmebaasid.toys.web.rest;

import ee.ttu.andmebaasid.toys.ToysApp;

import ee.ttu.andmebaasid.toys.domain.ProductView;
import ee.ttu.andmebaasid.toys.repository.ProductViewRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductViewResource REST controller.
 *
 * @see ProductViewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToysApp.class)
public class ProductViewResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_LENGTH = 1D;
    private static final Double UPDATED_LENGTH = 2D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_MIN_AGE = 1;
    private static final Integer UPDATED_MIN_AGE = 2;

    private static final Integer DEFAULT_MAX_AGE = 1;
    private static final Integer UPDATED_MAX_AGE = 2;

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PRODUCT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATOR_NAME = "BBBBBBBBBB";

    @Inject
    private ProductViewRepository productViewRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductViewMockMvc;

    private ProductView productView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductViewResource productViewResource = new ProductViewResource();
        ReflectionTestUtils.setField(productViewResource, "productViewRepository", productViewRepository);
        this.restProductViewMockMvc = MockMvcBuilders.standaloneSetup(productViewResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductView createEntity(EntityManager em) {
        ProductView productView = new ProductView()
                .code(DEFAULT_CODE)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .weight(DEFAULT_WEIGHT)
                .height(DEFAULT_HEIGHT)
                .length(DEFAULT_LENGTH)
                .width(DEFAULT_WIDTH)
                .price(DEFAULT_PRICE)
                .minAge(DEFAULT_MIN_AGE)
                .maxAge(DEFAULT_MAX_AGE)
                .countryName(DEFAULT_COUNTRY_NAME)
                .imageURL(DEFAULT_IMAGE_URL)
                .registrationDate(DEFAULT_REGISTRATION_DATE)
                .productState(DEFAULT_PRODUCT_STATE)
                .registratorName(DEFAULT_REGISTRATOR_NAME);
        return productView;
    }

    @Before
    public void initTest() {
        productView = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductView() throws Exception {
        int databaseSizeBeforeCreate = productViewRepository.findAll().size();

        // Create the ProductView

        restProductViewMockMvc.perform(post("/api/product-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productView)))
                .andExpect(status().isCreated());

        // Validate the ProductView in the database
        List<ProductView> productViews = productViewRepository.findAll();
        assertThat(productViews).hasSize(databaseSizeBeforeCreate + 1);
        ProductView testProductView = productViews.get(productViews.size() - 1);
        assertThat(testProductView.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductView.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductView.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductView.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProductView.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProductView.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testProductView.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testProductView.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductView.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testProductView.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
        assertThat(testProductView.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testProductView.getImageURL()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProductView.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testProductView.getProductState()).isEqualTo(DEFAULT_PRODUCT_STATE);
        assertThat(testProductView.getRegistratorName()).isEqualTo(DEFAULT_REGISTRATOR_NAME);
    }

    @Test
    @Transactional
    public void getAllProductViews() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViews
        restProductViewMockMvc.perform(get("/api/product-views?sort=code,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
                .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
                .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)))
                .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
                .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL.toString())))
                .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
                .andExpect(jsonPath("$.[*].productState").value(hasItem(DEFAULT_PRODUCT_STATE.toString())))
                .andExpect(jsonPath("$.[*].registratorName").value(hasItem(DEFAULT_REGISTRATOR_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProductView() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get the productView
        restProductViewMockMvc.perform(get("/api/product-views/{code}", productView.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.imageURL").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.productState").value(DEFAULT_PRODUCT_STATE.toString()))
            .andExpect(jsonPath("$.registratorName").value(DEFAULT_REGISTRATOR_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductView() throws Exception {
        // Get the productView
        restProductViewMockMvc.perform(get("/api/product-views/{code}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductView() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();

        // Update the productView
        ProductView updatedProductView = productViewRepository.findOne(productView.getCode());
        updatedProductView
                .code(UPDATED_CODE)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .weight(UPDATED_WEIGHT)
                .height(UPDATED_HEIGHT)
                .length(UPDATED_LENGTH)
                .width(UPDATED_WIDTH)
                .price(UPDATED_PRICE)
                .minAge(UPDATED_MIN_AGE)
                .maxAge(UPDATED_MAX_AGE)
                .countryName(UPDATED_COUNTRY_NAME)
                .imageURL(UPDATED_IMAGE_URL)
                .registrationDate(UPDATED_REGISTRATION_DATE)
                .productState(UPDATED_PRODUCT_STATE)
                .registratorName(UPDATED_REGISTRATOR_NAME);

        restProductViewMockMvc.perform(put("/api/product-views")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProductView)))
                .andExpect(status().isOk());

        // Validate the ProductView in the database
        List<ProductView> productViews = productViewRepository.findAll();
        assertThat(productViews).hasSize(databaseSizeBeforeUpdate);
        ProductView testProductView = productViews.get(productViews.size() - 1);
        assertThat(testProductView.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductView.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductView.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductView.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProductView.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProductView.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testProductView.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProductView.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductView.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testProductView.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testProductView.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testProductView.getImageURL()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductView.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testProductView.getProductState()).isEqualTo(UPDATED_PRODUCT_STATE);
        assertThat(testProductView.getRegistratorName()).isEqualTo(UPDATED_REGISTRATOR_NAME);
    }

    @Test
    @Transactional
    public void deleteProductView() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);
        int databaseSizeBeforeDelete = productViewRepository.findAll().size();

        // Get the productView
        restProductViewMockMvc.perform(delete("/api/product-views/{code}", productView.getCode())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductView> productViews = productViewRepository.findAll();
        assertThat(productViews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
