package com.cpi.jasperreport.service.mapper;

import com.cpi.jasperreport.domain.*;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JasperreportTemplateType and its DTO JasperreportTemplateTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JasperreportTemplateTypeMapper extends EntityMapper<JasperreportTemplateTypeDTO, JasperreportTemplateType> {



    default JasperreportTemplateType fromId(Long id) {
        if (id == null) {
            return null;
        }
        JasperreportTemplateType jasperreportTemplateType = new JasperreportTemplateType();
        jasperreportTemplateType.setId(id);
        return jasperreportTemplateType;
    }
}
