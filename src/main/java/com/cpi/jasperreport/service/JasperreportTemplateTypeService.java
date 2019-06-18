package com.cpi.jasperreport.service;

import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.jasperreport.domain.JasperreportTemplateType}.
 */
public interface JasperreportTemplateTypeService {

    /**
     * Save a jasperreportTemplateType.
     *
     * @param jasperreportTemplateTypeDTO the entity to save.
     * @return the persisted entity.
     */
    JasperreportTemplateTypeDTO save(JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO);

    /**
     * Get all the jasperreportTemplateTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JasperreportTemplateTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jasperreportTemplateType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JasperreportTemplateTypeDTO> findOne(Long id);

    /**
     * Delete the "id" jasperreportTemplateType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
