package com.cpi.jasperreport.service.impl;

import com.cpi.jasperreport.service.JasperreportTemplateTypeService;
import com.cpi.jasperreport.domain.JasperreportTemplateType;
import com.cpi.jasperreport.repository.JasperreportTemplateTypeRepository;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing JasperreportTemplateType.
 */
@Service
@Transactional
public class JasperreportTemplateTypeServiceImpl implements JasperreportTemplateTypeService {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateTypeServiceImpl.class);

    private final JasperreportTemplateTypeRepository jasperreportTemplateTypeRepository;

    private final JasperreportTemplateTypeMapper jasperreportTemplateTypeMapper;

    public JasperreportTemplateTypeServiceImpl(JasperreportTemplateTypeRepository jasperreportTemplateTypeRepository, JasperreportTemplateTypeMapper jasperreportTemplateTypeMapper) {
        this.jasperreportTemplateTypeRepository = jasperreportTemplateTypeRepository;
        this.jasperreportTemplateTypeMapper = jasperreportTemplateTypeMapper;
    }

    /**
     * Save a jasperreportTemplateType.
     *
     * @param jasperreportTemplateTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JasperreportTemplateTypeDTO save(JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO) {
        log.debug("Request to save JasperreportTemplateType : {}", jasperreportTemplateTypeDTO);
        JasperreportTemplateType jasperreportTemplateType = jasperreportTemplateTypeMapper.toEntity(jasperreportTemplateTypeDTO);
        jasperreportTemplateType = jasperreportTemplateTypeRepository.save(jasperreportTemplateType);
        return jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);
    }

    /**
     * Get all the jasperreportTemplateTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JasperreportTemplateTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JasperreportTemplateTypes");
        return jasperreportTemplateTypeRepository.findAll(pageable)
            .map(jasperreportTemplateTypeMapper::toDto);
    }

    /**
     * Get one jasperreportTemplateType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JasperreportTemplateTypeDTO findOne(Long id) {
        log.debug("Request to get JasperreportTemplateType : {}", id);
        JasperreportTemplateType jasperreportTemplateType = jasperreportTemplateTypeRepository.findOne(id);
        return jasperreportTemplateTypeMapper.toDto(jasperreportTemplateType);
    }

    /**
     * Delete the jasperreportTemplateType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JasperreportTemplateType : {}", id);
        jasperreportTemplateTypeRepository.delete(id);
    }
}
