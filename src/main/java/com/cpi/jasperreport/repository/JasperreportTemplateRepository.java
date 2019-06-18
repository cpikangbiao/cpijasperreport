package com.cpi.jasperreport.repository;

import com.cpi.jasperreport.domain.JasperreportTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JasperreportTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JasperreportTemplateRepository extends JpaRepository<JasperreportTemplate, Long>, JpaSpecificationExecutor<JasperreportTemplate> {

}
