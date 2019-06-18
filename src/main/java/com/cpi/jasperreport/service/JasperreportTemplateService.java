package com.cpi.jasperreport.service;

import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.jasperreport.domain.JasperreportTemplate}.
 */
public interface JasperreportTemplateService {

    /**
     * Save a jasperreportTemplate.
     *
     * @param jasperreportTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    JasperreportTemplateDTO save(JasperreportTemplateDTO jasperreportTemplateDTO);

    /**
     * Get all the jasperreportTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JasperreportTemplateDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jasperreportTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JasperreportTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" jasperreportTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
