package com.cpi.jasperreport.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.jasperreport.domain.JasperreportTemplateType;
import com.cpi.jasperreport.domain.*; // for static metamodels
import com.cpi.jasperreport.repository.JasperreportTemplateTypeRepository;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeCriteria;

import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateTypeMapper;

/**
 * Service for executing complex queries for JasperreportTemplateType entities in the database.
 * The main input is a {@link JasperreportTemplateTypeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JasperreportTemplateTypeDTO} or a {@link Page} of {@link JasperreportTemplateTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JasperreportTemplateTypeQueryService extends QueryService<JasperreportTemplateType> {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateTypeQueryService.class);


    private final JasperreportTemplateTypeRepository jasperreportTemplateTypeRepository;

    private final JasperreportTemplateTypeMapper jasperreportTemplateTypeMapper;

    public JasperreportTemplateTypeQueryService(JasperreportTemplateTypeRepository jasperreportTemplateTypeRepository, JasperreportTemplateTypeMapper jasperreportTemplateTypeMapper) {
        this.jasperreportTemplateTypeRepository = jasperreportTemplateTypeRepository;
        this.jasperreportTemplateTypeMapper = jasperreportTemplateTypeMapper;
    }

    /**
     * Return a {@link List} of {@link JasperreportTemplateTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JasperreportTemplateTypeDTO> findByCriteria(JasperreportTemplateTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<JasperreportTemplateType> specification = createSpecification(criteria);
        return jasperreportTemplateTypeMapper.toDto(jasperreportTemplateTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JasperreportTemplateTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JasperreportTemplateTypeDTO> findByCriteria(JasperreportTemplateTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<JasperreportTemplateType> specification = createSpecification(criteria);
        final Page<JasperreportTemplateType> result = jasperreportTemplateTypeRepository.findAll(specification, page);
        return result.map(jasperreportTemplateTypeMapper::toDto);
    }

    /**
     * Function to convert JasperreportTemplateTypeCriteria to a {@link Specifications}
     */
    private Specifications<JasperreportTemplateType> createSpecification(JasperreportTemplateTypeCriteria criteria) {
        Specifications<JasperreportTemplateType> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JasperreportTemplateType_.id));
            }
            if (criteria.getJasperreportTemplateTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJasperreportTemplateTypeName(), JasperreportTemplateType_.jasperreportTemplateTypeName));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), JasperreportTemplateType_.sortNum));
            }
        }
        return specification;
    }

}
