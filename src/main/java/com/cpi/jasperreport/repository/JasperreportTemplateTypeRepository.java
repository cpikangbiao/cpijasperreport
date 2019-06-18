package com.cpi.jasperreport.repository;

import com.cpi.jasperreport.domain.JasperreportTemplateType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JasperreportTemplateType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JasperreportTemplateTypeRepository extends JpaRepository<JasperreportTemplateType, Long>, JpaSpecificationExecutor<JasperreportTemplateType> {

}
