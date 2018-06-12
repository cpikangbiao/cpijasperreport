package com.cpi.jasperreport.service.impl;

import com.cpi.jasperreport.service.JasperreportTemplateService;
import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.repository.JasperreportTemplateRepository;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing JasperreportTemplate.
 */
@Service
@Transactional
public class JasperreportTemplateServiceImpl implements JasperreportTemplateService {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateServiceImpl.class);

    private final JasperreportTemplateRepository jasperreportTemplateRepository;

    private final JasperreportTemplateMapper jasperreportTemplateMapper;

    public JasperreportTemplateServiceImpl(JasperreportTemplateRepository jasperreportTemplateRepository, JasperreportTemplateMapper jasperreportTemplateMapper) {
        this.jasperreportTemplateRepository = jasperreportTemplateRepository;
        this.jasperreportTemplateMapper = jasperreportTemplateMapper;
    }

    /**
     * Save a jasperreportTemplate.
     *
     * @param jasperreportTemplateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JasperreportTemplateDTO save(JasperreportTemplateDTO jasperreportTemplateDTO) {
        log.debug("Request to save JasperreportTemplate : {}", jasperreportTemplateDTO);
        JasperreportTemplate jasperreportTemplate = jasperreportTemplateMapper.toEntity(jasperreportTemplateDTO);
        jasperreportTemplate = jasperreportTemplateRepository.save(jasperreportTemplate);
        return jasperreportTemplateMapper.toDto(jasperreportTemplate);
    }

    /**
     * Get all the jasperreportTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JasperreportTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JasperreportTemplates");
        return jasperreportTemplateRepository.findAll(pageable)
            .map(jasperreportTemplateMapper::toDto);
    }

    /**
     * Get one jasperreportTemplate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JasperreportTemplateDTO findOne(Long id) {
        log.debug("Request to get JasperreportTemplate : {}", id);
        JasperreportTemplate jasperreportTemplate = jasperreportTemplateRepository.findOne(id);
        return jasperreportTemplateMapper.toDto(jasperreportTemplate);
    }

    /**
     * Delete the jasperreportTemplate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JasperreportTemplate : {}", id);
        jasperreportTemplateRepository.delete(id);
    }
}
