package com.cpi.jasperreport.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.domain.*; // for static metamodels
import com.cpi.jasperreport.repository.JasperreportTemplateRepository;
import com.cpi.jasperreport.service.dto.JasperreportTemplateCriteria;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateMapper;

/**
 * Service for executing complex queries for {@link JasperreportTemplate} entities in the database.
 * The main input is a {@link JasperreportTemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JasperreportTemplateDTO} or a {@link Page} of {@link JasperreportTemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JasperreportTemplateQueryService extends QueryService<JasperreportTemplate> {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateQueryService.class);

    private final JasperreportTemplateRepository jasperreportTemplateRepository;

    private final JasperreportTemplateMapper jasperreportTemplateMapper;

    public JasperreportTemplateQueryService(JasperreportTemplateRepository jasperreportTemplateRepository, JasperreportTemplateMapper jasperreportTemplateMapper) {
        this.jasperreportTemplateRepository = jasperreportTemplateRepository;
        this.jasperreportTemplateMapper = jasperreportTemplateMapper;
    }

    /**
     * Return a {@link List} of {@link JasperreportTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JasperreportTemplateDTO> findByCriteria(JasperreportTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JasperreportTemplate> specification = createSpecification(criteria);
        return jasperreportTemplateMapper.toDto(jasperreportTemplateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JasperreportTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JasperreportTemplateDTO> findByCriteria(JasperreportTemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JasperreportTemplate> specification = createSpecification(criteria);
        return jasperreportTemplateRepository.findAll(specification, page)
            .map(jasperreportTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JasperreportTemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JasperreportTemplate> specification = createSpecification(criteria);
        return jasperreportTemplateRepository.count(specification);
    }

    /**
     * Function to convert JasperreportTemplateCriteria to a {@link Specification}.
     */
    private Specification<JasperreportTemplate> createSpecification(JasperreportTemplateCriteria criteria) {
        Specification<JasperreportTemplate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JasperreportTemplate_.id));
            }
            if (criteria.getJasperreportTemplateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJasperreportTemplateName(), JasperreportTemplate_.jasperreportTemplateName));
            }
            if (criteria.getJasperreportTemplateFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJasperreportTemplateFileName(), JasperreportTemplate_.jasperreportTemplateFileName));
            }
            if (criteria.getCorrespondentBillDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCorrespondentBillDate(), JasperreportTemplate_.correspondentBillDate));
            }
            if (criteria.getIsUse() != null) {
                specification = specification.and(buildSpecification(criteria.getIsUse(), JasperreportTemplate_.isUse));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), JasperreportTemplate_.version));
            }
            if (criteria.getJasperreportTemplateTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getJasperreportTemplateTypeId(),
                    root -> root.join(JasperreportTemplate_.jasperreportTemplateType, JoinType.LEFT).get(JasperreportTemplateType_.id)));
            }
        }
        return specification;
    }
}
