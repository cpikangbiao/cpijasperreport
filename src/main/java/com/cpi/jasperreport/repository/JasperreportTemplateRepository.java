package com.cpi.jasperreport.repository;

import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.domain.JasperreportTemplateType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JasperreportTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JasperreportTemplateRepository extends JpaRepository<JasperreportTemplate, Long>, JpaSpecificationExecutor<JasperreportTemplate> {

    JasperreportTemplate findTopByIsUseTrueAndJasperreportTemplateTypeOrderByVersionDesc(JasperreportTemplateType jasperreportTemplateType);
}
