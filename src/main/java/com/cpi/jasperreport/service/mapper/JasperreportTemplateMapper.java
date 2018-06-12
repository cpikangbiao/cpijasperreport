package com.cpi.jasperreport.service.mapper;

import com.cpi.jasperreport.domain.*;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JasperreportTemplate and its DTO JasperreportTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {JasperreportTemplateTypeMapper.class})
public interface JasperreportTemplateMapper extends EntityMapper<JasperreportTemplateDTO, JasperreportTemplate> {

    @Mapping(source = "jasperreportTemplateType.id", target = "jasperreportTemplateTypeId")
    @Mapping(source = "jasperreportTemplateType.jasperreportTemplateTypeName", target = "jasperreportTemplateTypeJasperreportTemplateTypeName")
    JasperreportTemplateDTO toDto(JasperreportTemplate jasperreportTemplate);

    @Mapping(source = "jasperreportTemplateTypeId", target = "jasperreportTemplateType")
    JasperreportTemplate toEntity(JasperreportTemplateDTO jasperreportTemplateDTO);

    default JasperreportTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        JasperreportTemplate jasperreportTemplate = new JasperreportTemplate();
        jasperreportTemplate.setId(id);
        return jasperreportTemplate;
    }
}
