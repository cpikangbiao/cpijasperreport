package com.cpi.jasperreport.web.rest;

import com.cpi.jasperreport.CpijasperreportApp;
import com.cpi.jasperreport.config.SecurityBeanOverrideConfiguration;
import com.cpi.jasperreport.domain.JasperreportTemplateType;
import com.cpi.jasperreport.repository.JasperreportTemplateTypeRepository;
import com.cpi.jasperreport.service.JasperreportTemplateTypeService;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateTypeMapper;
import com.cpi.jasperreport.web.rest.errors.ExceptionTranslator;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeCriteria;
import com.cpi.jasperreport.service.JasperreportTemplateTypeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cpi.jasperreport.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link JasperreportTemplateTypeResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpijasperreportApp.class})
public class JasperreportTemplateTypeResourceIT {

    private static final String DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private JasperreportTemplateTypeRepository jasperreportTemplateTypeRepository;

    @Autowired
    private JasperreportTemplateTypeMapper jasperreportTemplateTypeMapper;

    @Autowired
    private JasperreportTemplateTypeService jasperreportTemplateTypeService;

    @Autowired
    private JasperreportTemplateTypeQueryService jasperreportTemplateTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restJasperreportTemplateTypeMockMvc;

    private JasperreportTemplateType jasperreportTemplateType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JasperreportTemplateTypeResource jasperreportTemplateTypeResource = new JasperreportTemplateTypeResource(jasperreportTemplateTypeService, jasperreportTemplateTypeQueryService);
        this.restJasperreportTemplateTypeMockMvc = MockMvcBuilders.standaloneSetup(jasperreportTemplateTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JasperreportTemplateType createEntity(EntityManager em) {
        JasperreportTemplateType jasperreportTemplateType = new JasperreportTemplateType()
            .jasperreportTemplateTypeName(DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME)
            .sortNum(DEFAULT_SORT_NUM);
        return jasperreportTemplateType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JasperreportTemplateType createUpdatedEntity(EntityManager em) {
        JasperreportTemplateType jasperreportTemplateType = new JasperreportTemplateType()
            .jasperreportTemplateTypeName(UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME)
            .sortNum(UPDATED_SORT_NUM);
        return jasperreportTemplateType;
    }

    @BeforeEach
    public void initTest() {
        jasperreportTemplateType = createEntity(em);
    }

    @Test
    @Transactional
    public void createJasperreportTemplateType() throws Exception {
        int databaseSizeBeforeCreate = jasperreportTemplateTypeRepository.findAll().size();

        // Create the JasperreportTemplateType
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);
        restJasperreportTemplateTypeMockMvc.perform(post("/api/jasperreport-template-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the JasperreportTemplateType in the database
        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        JasperreportTemplateType testJasperreportTemplateType = jasperreportTemplateTypeList.get(jasperreportTemplateTypeList.size() - 1);
        assertThat(testJasperreportTemplateType.getJasperreportTemplateTypeName()).isEqualTo(DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME);
        assertThat(testJasperreportTemplateType.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createJasperreportTemplateTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jasperreportTemplateTypeRepository.findAll().size();

        // Create the JasperreportTemplateType with an existing ID
        jasperreportTemplateType.setId(1L);
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJasperreportTemplateTypeMockMvc.perform(post("/api/jasperreport-template-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JasperreportTemplateType in the database
        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkJasperreportTemplateTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperreportTemplateTypeRepository.findAll().size();
        // set the field null
        jasperreportTemplateType.setJasperreportTemplateTypeName(null);

        // Create the JasperreportTemplateType, which fails.
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);

        restJasperreportTemplateTypeMockMvc.perform(post("/api/jasperreport-template-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateTypeDTO)))
            .andExpect(status().isBadRequest());

        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = jasperreportTemplateTypeRepository.findAll().size();
        // set the field null
        jasperreportTemplateType.setSortNum(null);

        // Create the JasperreportTemplateType, which fails.
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);

        restJasperreportTemplateTypeMockMvc.perform(post("/api/jasperreport-template-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateTypeDTO)))
            .andExpect(status().isBadRequest());

        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypes() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jasperreportTemplateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].jasperreportTemplateTypeName").value(hasItem(DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }
    
    @Test
    @Transactional
    public void getJasperreportTemplateType() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get the jasperreportTemplateType
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types/{id}", jasperreportTemplateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jasperreportTemplateType.getId().intValue()))
            .andExpect(jsonPath("$.jasperreportTemplateTypeName").value(DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesByJasperreportTemplateTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where jasperreportTemplateTypeName equals to DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME
        defaultJasperreportTemplateTypeShouldBeFound("jasperreportTemplateTypeName.equals=" + DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME);

        // Get all the jasperreportTemplateTypeList where jasperreportTemplateTypeName equals to UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME
        defaultJasperreportTemplateTypeShouldNotBeFound("jasperreportTemplateTypeName.equals=" + UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesByJasperreportTemplateTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where jasperreportTemplateTypeName in DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME or UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME
        defaultJasperreportTemplateTypeShouldBeFound("jasperreportTemplateTypeName.in=" + DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME + "," + UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME);

        // Get all the jasperreportTemplateTypeList where jasperreportTemplateTypeName equals to UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME
        defaultJasperreportTemplateTypeShouldNotBeFound("jasperreportTemplateTypeName.in=" + UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesByJasperreportTemplateTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where jasperreportTemplateTypeName is not null
        defaultJasperreportTemplateTypeShouldBeFound("jasperreportTemplateTypeName.specified=true");

        // Get all the jasperreportTemplateTypeList where jasperreportTemplateTypeName is null
        defaultJasperreportTemplateTypeShouldNotBeFound("jasperreportTemplateTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where sortNum equals to DEFAULT_SORT_NUM
        defaultJasperreportTemplateTypeShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the jasperreportTemplateTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultJasperreportTemplateTypeShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultJasperreportTemplateTypeShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the jasperreportTemplateTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultJasperreportTemplateTypeShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where sortNum is not null
        defaultJasperreportTemplateTypeShouldBeFound("sortNum.specified=true");

        // Get all the jasperreportTemplateTypeList where sortNum is null
        defaultJasperreportTemplateTypeShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultJasperreportTemplateTypeShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the jasperreportTemplateTypeList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultJasperreportTemplateTypeShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplateTypesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        // Get all the jasperreportTemplateTypeList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultJasperreportTemplateTypeShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the jasperreportTemplateTypeList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultJasperreportTemplateTypeShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJasperreportTemplateTypeShouldBeFound(String filter) throws Exception {
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jasperreportTemplateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].jasperreportTemplateTypeName").value(hasItem(DEFAULT_JASPERREPORT_TEMPLATE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));

        // Check, that the count call also returns 1
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJasperreportTemplateTypeShouldNotBeFound(String filter) throws Exception {
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingJasperreportTemplateType() throws Exception {
        // Get the jasperreportTemplateType
        restJasperreportTemplateTypeMockMvc.perform(get("/api/jasperreport-template-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJasperreportTemplateType() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        int databaseSizeBeforeUpdate = jasperreportTemplateTypeRepository.findAll().size();

        // Update the jasperreportTemplateType
        JasperreportTemplateType updatedJasperreportTemplateType = jasperreportTemplateTypeRepository.findById(jasperreportTemplateType.getId()).get();
        // Disconnect from session so that the updates on updatedJasperreportTemplateType are not directly saved in db
        em.detach(updatedJasperreportTemplateType);
        updatedJasperreportTemplateType
            .jasperreportTemplateTypeName(UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME)
            .sortNum(UPDATED_SORT_NUM);
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeMapper.toDto(updatedJasperreportTemplateType);

        restJasperreportTemplateTypeMockMvc.perform(put("/api/jasperreport-template-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateTypeDTO)))
            .andExpect(status().isOk());

        // Validate the JasperreportTemplateType in the database
        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeUpdate);
        JasperreportTemplateType testJasperreportTemplateType = jasperreportTemplateTypeList.get(jasperreportTemplateTypeList.size() - 1);
        assertThat(testJasperreportTemplateType.getJasperreportTemplateTypeName()).isEqualTo(UPDATED_JASPERREPORT_TEMPLATE_TYPE_NAME);
        assertThat(testJasperreportTemplateType.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingJasperreportTemplateType() throws Exception {
        int databaseSizeBeforeUpdate = jasperreportTemplateTypeRepository.findAll().size();

        // Create the JasperreportTemplateType
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJasperreportTemplateTypeMockMvc.perform(put("/api/jasperreport-template-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JasperreportTemplateType in the database
        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJasperreportTemplateType() throws Exception {
        // Initialize the database
        jasperreportTemplateTypeRepository.saveAndFlush(jasperreportTemplateType);

        int databaseSizeBeforeDelete = jasperreportTemplateTypeRepository.findAll().size();

        // Delete the jasperreportTemplateType
        restJasperreportTemplateTypeMockMvc.perform(delete("/api/jasperreport-template-types/{id}", jasperreportTemplateType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<JasperreportTemplateType> jasperreportTemplateTypeList = jasperreportTemplateTypeRepository.findAll();
        assertThat(jasperreportTemplateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JasperreportTemplateType.class);
        JasperreportTemplateType jasperreportTemplateType1 = new JasperreportTemplateType();
        jasperreportTemplateType1.setId(1L);
        JasperreportTemplateType jasperreportTemplateType2 = new JasperreportTemplateType();
        jasperreportTemplateType2.setId(jasperreportTemplateType1.getId());
        assertThat(jasperreportTemplateType1).isEqualTo(jasperreportTemplateType2);
        jasperreportTemplateType2.setId(2L);
        assertThat(jasperreportTemplateType1).isNotEqualTo(jasperreportTemplateType2);
        jasperreportTemplateType1.setId(null);
        assertThat(jasperreportTemplateType1).isNotEqualTo(jasperreportTemplateType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JasperreportTemplateTypeDTO.class);
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO1 = new JasperreportTemplateTypeDTO();
        jasperreportTemplateTypeDTO1.setId(1L);
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO2 = new JasperreportTemplateTypeDTO();
        assertThat(jasperreportTemplateTypeDTO1).isNotEqualTo(jasperreportTemplateTypeDTO2);
        jasperreportTemplateTypeDTO2.setId(jasperreportTemplateTypeDTO1.getId());
        assertThat(jasperreportTemplateTypeDTO1).isEqualTo(jasperreportTemplateTypeDTO2);
        jasperreportTemplateTypeDTO2.setId(2L);
        assertThat(jasperreportTemplateTypeDTO1).isNotEqualTo(jasperreportTemplateTypeDTO2);
        jasperreportTemplateTypeDTO1.setId(null);
        assertThat(jasperreportTemplateTypeDTO1).isNotEqualTo(jasperreportTemplateTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jasperreportTemplateTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jasperreportTemplateTypeMapper.fromId(null)).isNull();
    }
}
