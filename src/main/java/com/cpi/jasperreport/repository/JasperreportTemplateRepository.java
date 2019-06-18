package com.cpi.jasperreport.repository;

import com.cpi.jasperreport.domain.JasperreportTemplate;

import com.cpi.jasperreport.domain.JasperreportTemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JasperreportTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JasperreportTemplateRepository extends JpaRepository<JasperreportTemplate, Long>, JpaSpecificationExecutor<JasperreportTemplate> {

    JasperreportTemplate findTopByIsUseTrueAndJasperreportTemplateTypeOrderByVersionDesc(JasperreportTemplateType jasperreportTemplateType);
}
