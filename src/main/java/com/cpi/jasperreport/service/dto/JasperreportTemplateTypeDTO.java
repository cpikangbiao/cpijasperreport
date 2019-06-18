package com.cpi.jasperreport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cpi.jasperreport.domain.JasperreportTemplateType} entity.
 */
public class JasperreportTemplateTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String jasperreportTemplateTypeName;

    @NotNull
    private Integer sortNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJasperreportTemplateTypeName() {
        return jasperreportTemplateTypeName;
    }

    public void setJasperreportTemplateTypeName(String jasperreportTemplateTypeName) {
        this.jasperreportTemplateTypeName = jasperreportTemplateTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = (JasperreportTemplateTypeDTO) o;
        if (jasperreportTemplateTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jasperreportTemplateTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JasperreportTemplateTypeDTO{" +
            "id=" + getId() +
            ", jasperreportTemplateTypeName='" + getJasperreportTemplateTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
