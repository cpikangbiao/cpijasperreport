package com.cpi.jasperreport.web.rest;

import com.cpi.jasperreport.CpijasperreportApp;

import com.cpi.jasperreport.config.SecurityBeanOverrideConfiguration;

import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.domain.JasperreportTemplateType;
import com.cpi.jasperreport.repository.JasperreportTemplateRepository;
import com.cpi.jasperreport.service.JasperreportTemplateService;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateMapper;
import com.cpi.jasperreport.web.rest.errors.ExceptionTranslator;
import com.cpi.jasperreport.service.dto.JasperreportTemplateCriteria;
import com.cpi.jasperreport.service.JasperreportTemplateQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cpi.jasperreport.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JasperreportTemplateResource REST controller.
 *
 * @see JasperreportTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpijasperreportApp.class, SecurityBeanOverrideConfiguration.class})
public class JasperreportTemplateResourceIntTest {

    private static final String DEFAULT_JASPERREPORT_TEMPLATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JASPERREPORT_TEMPLATE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_JASPERREPORT_TEMPLATE_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_JASPERREPORT_TEMPLATE_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CORRESPONDENT_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CORRESPONDENT_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_USE = false;
    private static final Boolean UPDATED_IS_USE = true;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private JasperreportTemplateRepository jasperreportTemplateRepository;

    @Autowired
    private JasperreportTemplateMapper jasperreportTemplateMapper;

    @Autowired
    private JasperreportTemplateService jasperreportTemplateService;

    @Autowired
    private JasperreportTemplateQueryService jasperreportTemplateQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJasperreportTemplateMockMvc;

    private JasperreportTemplate jasperreportTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JasperreportTemplateResource jasperreportTemplateResource = new JasperreportTemplateResource(jasperreportTemplateService, jasperreportTemplateQueryService);
        this.restJasperreportTemplateMockMvc = MockMvcBuilders.standaloneSetup(jasperreportTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JasperreportTemplate createEntity(EntityManager em) {
        JasperreportTemplate jasperreportTemplate = new JasperreportTemplate()
            .jasperreportTemplateName(DEFAULT_JASPERREPORT_TEMPLATE_NAME)
            .jasperreportTemplateFile(DEFAULT_JASPERREPORT_TEMPLATE_FILE)
            .jasperreportTemplateFileContentType(DEFAULT_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE)
            .correspondentBillDate(DEFAULT_CORRESPONDENT_BILL_DATE)
            .isUse(DEFAULT_IS_USE)
            .version(DEFAULT_VERSION);
        return jasperreportTemplate;
    }

    @Before
    public void initTest() {
        jasperreportTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createJasperreportTemplate() throws Exception {
        int databaseSizeBeforeCreate = jasperreportTemplateRepository.findAll().size();

        // Create the JasperreportTemplate
        JasperreportTemplateDTO jasperreportTemplateDTO = jasperreportTemplateMapper.toDto(jasperreportTemplate);
        restJasperreportTemplateMockMvc.perform(post("/api/jasperreport-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the JasperreportTemplate in the database
        List<JasperreportTemplate> jasperreportTemplateList = jasperreportTemplateRepository.findAll();
        assertThat(jasperreportTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        JasperreportTemplate testJasperreportTemplate = jasperreportTemplateList.get(jasperreportTemplateList.size() - 1);
        assertThat(testJasperreportTemplate.getJasperreportTemplateName()).isEqualTo(DEFAULT_JASPERREPORT_TEMPLATE_NAME);
        assertThat(testJasperreportTemplate.getJasperreportTemplateFile()).isEqualTo(DEFAULT_JASPERREPORT_TEMPLATE_FILE);
        assertThat(testJasperreportTemplate.getJasperreportTemplateFileContentType()).isEqualTo(DEFAULT_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE);
        assertThat(testJasperreportTemplate.getCorrespondentBillDate()).isEqualTo(DEFAULT_CORRESPONDENT_BILL_DATE);
        assertThat(testJasperreportTemplate.isIsUse()).isEqualTo(DEFAULT_IS_USE);
        assertThat(testJasperreportTemplate.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createJasperreportTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jasperreportTemplateRepository.findAll().size();

        // Create the JasperreportTemplate with an existing ID
        jasperreportTemplate.setId(1L);
        JasperreportTemplateDTO jasperreportTemplateDTO = jasperreportTemplateMapper.toDto(jasperreportTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJasperreportTemplateMockMvc.perform(post("/api/jasperreport-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JasperreportTemplate in the database
        List<JasperreportTemplate> jasperreportTemplateList = jasperreportTemplateRepository.findAll();
        assertThat(jasperreportTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplates() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList
        restJasperreportTemplateMockMvc.perform(get("/api/jasperreport-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jasperreportTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].jasperreportTemplateName").value(hasItem(DEFAULT_JASPERREPORT_TEMPLATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].jasperreportTemplateFileContentType").value(hasItem(DEFAULT_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].jasperreportTemplateFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_JASPERREPORT_TEMPLATE_FILE))))
            .andExpect(jsonPath("$.[*].correspondentBillDate").value(hasItem(DEFAULT_CORRESPONDENT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].isUse").value(hasItem(DEFAULT_IS_USE.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    @Test
    @Transactional
    public void getJasperreportTemplate() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get the jasperreportTemplate
        restJasperreportTemplateMockMvc.perform(get("/api/jasperreport-templates/{id}", jasperreportTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jasperreportTemplate.getId().intValue()))
            .andExpect(jsonPath("$.jasperreportTemplateName").value(DEFAULT_JASPERREPORT_TEMPLATE_NAME.toString()))
            .andExpect(jsonPath("$.jasperreportTemplateFileContentType").value(DEFAULT_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.jasperreportTemplateFile").value(Base64Utils.encodeToString(DEFAULT_JASPERREPORT_TEMPLATE_FILE)))
            .andExpect(jsonPath("$.correspondentBillDate").value(DEFAULT_CORRESPONDENT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.isUse").value(DEFAULT_IS_USE.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByJasperreportTemplateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where jasperreportTemplateName equals to DEFAULT_JASPERREPORT_TEMPLATE_NAME
        defaultJasperreportTemplateShouldBeFound("jasperreportTemplateName.equals=" + DEFAULT_JASPERREPORT_TEMPLATE_NAME);

        // Get all the jasperreportTemplateList where jasperreportTemplateName equals to UPDATED_JASPERREPORT_TEMPLATE_NAME
        defaultJasperreportTemplateShouldNotBeFound("jasperreportTemplateName.equals=" + UPDATED_JASPERREPORT_TEMPLATE_NAME);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByJasperreportTemplateNameIsInShouldWork() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where jasperreportTemplateName in DEFAULT_JASPERREPORT_TEMPLATE_NAME or UPDATED_JASPERREPORT_TEMPLATE_NAME
        defaultJasperreportTemplateShouldBeFound("jasperreportTemplateName.in=" + DEFAULT_JASPERREPORT_TEMPLATE_NAME + "," + UPDATED_JASPERREPORT_TEMPLATE_NAME);

        // Get all the jasperreportTemplateList where jasperreportTemplateName equals to UPDATED_JASPERREPORT_TEMPLATE_NAME
        defaultJasperreportTemplateShouldNotBeFound("jasperreportTemplateName.in=" + UPDATED_JASPERREPORT_TEMPLATE_NAME);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByJasperreportTemplateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where jasperreportTemplateName is not null
        defaultJasperreportTemplateShouldBeFound("jasperreportTemplateName.specified=true");

        // Get all the jasperreportTemplateList where jasperreportTemplateName is null
        defaultJasperreportTemplateShouldNotBeFound("jasperreportTemplateName.specified=false");
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByCorrespondentBillDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where correspondentBillDate equals to DEFAULT_CORRESPONDENT_BILL_DATE
        defaultJasperreportTemplateShouldBeFound("correspondentBillDate.equals=" + DEFAULT_CORRESPONDENT_BILL_DATE);

        // Get all the jasperreportTemplateList where correspondentBillDate equals to UPDATED_CORRESPONDENT_BILL_DATE
        defaultJasperreportTemplateShouldNotBeFound("correspondentBillDate.equals=" + UPDATED_CORRESPONDENT_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByCorrespondentBillDateIsInShouldWork() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where correspondentBillDate in DEFAULT_CORRESPONDENT_BILL_DATE or UPDATED_CORRESPONDENT_BILL_DATE
        defaultJasperreportTemplateShouldBeFound("correspondentBillDate.in=" + DEFAULT_CORRESPONDENT_BILL_DATE + "," + UPDATED_CORRESPONDENT_BILL_DATE);

        // Get all the jasperreportTemplateList where correspondentBillDate equals to UPDATED_CORRESPONDENT_BILL_DATE
        defaultJasperreportTemplateShouldNotBeFound("correspondentBillDate.in=" + UPDATED_CORRESPONDENT_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByCorrespondentBillDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where correspondentBillDate is not null
        defaultJasperreportTemplateShouldBeFound("correspondentBillDate.specified=true");

        // Get all the jasperreportTemplateList where correspondentBillDate is null
        defaultJasperreportTemplateShouldNotBeFound("correspondentBillDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByIsUseIsEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where isUse equals to DEFAULT_IS_USE
        defaultJasperreportTemplateShouldBeFound("isUse.equals=" + DEFAULT_IS_USE);

        // Get all the jasperreportTemplateList where isUse equals to UPDATED_IS_USE
        defaultJasperreportTemplateShouldNotBeFound("isUse.equals=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByIsUseIsInShouldWork() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where isUse in DEFAULT_IS_USE or UPDATED_IS_USE
        defaultJasperreportTemplateShouldBeFound("isUse.in=" + DEFAULT_IS_USE + "," + UPDATED_IS_USE);

        // Get all the jasperreportTemplateList where isUse equals to UPDATED_IS_USE
        defaultJasperreportTemplateShouldNotBeFound("isUse.in=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByIsUseIsNullOrNotNull() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where isUse is not null
        defaultJasperreportTemplateShouldBeFound("isUse.specified=true");

        // Get all the jasperreportTemplateList where isUse is null
        defaultJasperreportTemplateShouldNotBeFound("isUse.specified=false");
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where version equals to DEFAULT_VERSION
        defaultJasperreportTemplateShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the jasperreportTemplateList where version equals to UPDATED_VERSION
        defaultJasperreportTemplateShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultJasperreportTemplateShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the jasperreportTemplateList where version equals to UPDATED_VERSION
        defaultJasperreportTemplateShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where version is not null
        defaultJasperreportTemplateShouldBeFound("version.specified=true");

        // Get all the jasperreportTemplateList where version is null
        defaultJasperreportTemplateShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where version greater than or equals to DEFAULT_VERSION
        defaultJasperreportTemplateShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the jasperreportTemplateList where version greater than or equals to UPDATED_VERSION
        defaultJasperreportTemplateShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllJasperreportTemplatesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);

        // Get all the jasperreportTemplateList where version less than or equals to DEFAULT_VERSION
        defaultJasperreportTemplateShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the jasperreportTemplateList where version less than or equals to UPDATED_VERSION
        defaultJasperreportTemplateShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllJasperreportTemplatesByJasperreportTemplateTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        JasperreportTemplateType jasperreportTemplateType = JasperreportTemplateTypeResourceIntTest.createEntity(em);
        em.persist(jasperreportTemplateType);
        em.flush();
        jasperreportTemplate.setJasperreportTemplateType(jasperreportTemplateType);
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);
        Long jasperreportTemplateTypeId = jasperreportTemplateType.getId();

        // Get all the jasperreportTemplateList where jasperreportTemplateType equals to jasperreportTemplateTypeId
        defaultJasperreportTemplateShouldBeFound("jasperreportTemplateTypeId.equals=" + jasperreportTemplateTypeId);

        // Get all the jasperreportTemplateList where jasperreportTemplateType equals to jasperreportTemplateTypeId + 1
        defaultJasperreportTemplateShouldNotBeFound("jasperreportTemplateTypeId.equals=" + (jasperreportTemplateTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultJasperreportTemplateShouldBeFound(String filter) throws Exception {
        restJasperreportTemplateMockMvc.perform(get("/api/jasperreport-templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jasperreportTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].jasperreportTemplateName").value(hasItem(DEFAULT_JASPERREPORT_TEMPLATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].jasperreportTemplateFileContentType").value(hasItem(DEFAULT_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].jasperreportTemplateFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_JASPERREPORT_TEMPLATE_FILE))))
            .andExpect(jsonPath("$.[*].correspondentBillDate").value(hasItem(DEFAULT_CORRESPONDENT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].isUse").value(hasItem(DEFAULT_IS_USE.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultJasperreportTemplateShouldNotBeFound(String filter) throws Exception {
        restJasperreportTemplateMockMvc.perform(get("/api/jasperreport-templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingJasperreportTemplate() throws Exception {
        // Get the jasperreportTemplate
        restJasperreportTemplateMockMvc.perform(get("/api/jasperreport-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJasperreportTemplate() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);
        int databaseSizeBeforeUpdate = jasperreportTemplateRepository.findAll().size();

        // Update the jasperreportTemplate
        JasperreportTemplate updatedJasperreportTemplate = jasperreportTemplateRepository.findOne(jasperreportTemplate.getId());
        // Disconnect from session so that the updates on updatedJasperreportTemplate are not directly saved in db
        em.detach(updatedJasperreportTemplate);
        updatedJasperreportTemplate
            .jasperreportTemplateName(UPDATED_JASPERREPORT_TEMPLATE_NAME)
            .jasperreportTemplateFile(UPDATED_JASPERREPORT_TEMPLATE_FILE)
            .jasperreportTemplateFileContentType(UPDATED_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE)
            .correspondentBillDate(UPDATED_CORRESPONDENT_BILL_DATE)
            .isUse(UPDATED_IS_USE)
            .version(UPDATED_VERSION);
        JasperreportTemplateDTO jasperreportTemplateDTO = jasperreportTemplateMapper.toDto(updatedJasperreportTemplate);

        restJasperreportTemplateMockMvc.perform(put("/api/jasperreport-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the JasperreportTemplate in the database
        List<JasperreportTemplate> jasperreportTemplateList = jasperreportTemplateRepository.findAll();
        assertThat(jasperreportTemplateList).hasSize(databaseSizeBeforeUpdate);
        JasperreportTemplate testJasperreportTemplate = jasperreportTemplateList.get(jasperreportTemplateList.size() - 1);
        assertThat(testJasperreportTemplate.getJasperreportTemplateName()).isEqualTo(UPDATED_JASPERREPORT_TEMPLATE_NAME);
        assertThat(testJasperreportTemplate.getJasperreportTemplateFile()).isEqualTo(UPDATED_JASPERREPORT_TEMPLATE_FILE);
        assertThat(testJasperreportTemplate.getJasperreportTemplateFileContentType()).isEqualTo(UPDATED_JASPERREPORT_TEMPLATE_FILE_CONTENT_TYPE);
        assertThat(testJasperreportTemplate.getCorrespondentBillDate()).isEqualTo(UPDATED_CORRESPONDENT_BILL_DATE);
        assertThat(testJasperreportTemplate.isIsUse()).isEqualTo(UPDATED_IS_USE);
        assertThat(testJasperreportTemplate.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingJasperreportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = jasperreportTemplateRepository.findAll().size();

        // Create the JasperreportTemplate
        JasperreportTemplateDTO jasperreportTemplateDTO = jasperreportTemplateMapper.toDto(jasperreportTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJasperreportTemplateMockMvc.perform(put("/api/jasperreport-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jasperreportTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the JasperreportTemplate in the database
        List<JasperreportTemplate> jasperreportTemplateList = jasperreportTemplateRepository.findAll();
        assertThat(jasperreportTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJasperreportTemplate() throws Exception {
        // Initialize the database
        jasperreportTemplateRepository.saveAndFlush(jasperreportTemplate);
        int databaseSizeBeforeDelete = jasperreportTemplateRepository.findAll().size();

        // Get the jasperreportTemplate
        restJasperreportTemplateMockMvc.perform(delete("/api/jasperreport-templates/{id}", jasperreportTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JasperreportTemplate> jasperreportTemplateList = jasperreportTemplateRepository.findAll();
        assertThat(jasperreportTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JasperreportTemplate.class);
        JasperreportTemplate jasperreportTemplate1 = new JasperreportTemplate();
        jasperreportTemplate1.setId(1L);
        JasperreportTemplate jasperreportTemplate2 = new JasperreportTemplate();
        jasperreportTemplate2.setId(jasperreportTemplate1.getId());
        assertThat(jasperreportTemplate1).isEqualTo(jasperreportTemplate2);
        jasperreportTemplate2.setId(2L);
        assertThat(jasperreportTemplate1).isNotEqualTo(jasperreportTemplate2);
        jasperreportTemplate1.setId(null);
        assertThat(jasperreportTemplate1).isNotEqualTo(jasperreportTemplate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JasperreportTemplateDTO.class);
        JasperreportTemplateDTO jasperreportTemplateDTO1 = new JasperreportTemplateDTO();
        jasperreportTemplateDTO1.setId(1L);
        JasperreportTemplateDTO jasperreportTemplateDTO2 = new JasperreportTemplateDTO();
        assertThat(jasperreportTemplateDTO1).isNotEqualTo(jasperreportTemplateDTO2);
        jasperreportTemplateDTO2.setId(jasperreportTemplateDTO1.getId());
        assertThat(jasperreportTemplateDTO1).isEqualTo(jasperreportTemplateDTO2);
        jasperreportTemplateDTO2.setId(2L);
        assertThat(jasperreportTemplateDTO1).isNotEqualTo(jasperreportTemplateDTO2);
        jasperreportTemplateDTO1.setId(null);
        assertThat(jasperreportTemplateDTO1).isNotEqualTo(jasperreportTemplateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jasperreportTemplateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jasperreportTemplateMapper.fromId(null)).isNull();
    }
}
