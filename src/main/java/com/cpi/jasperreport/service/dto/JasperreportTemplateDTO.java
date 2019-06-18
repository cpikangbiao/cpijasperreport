package com.cpi.jasperreport.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the JasperreportTemplate entity.
 */
public class JasperreportTemplateDTO implements Serializable {

    private Long id;

    private String jasperreportTemplateName;

    private String jasperreportTemplateFileName;

    @Lob
    private byte[] jasperreportTemplateFile;
    private String jasperreportTemplateFileContentType;

    private Instant correspondentBillDate;

    private Boolean isUse;

    private Integer version;

    private Long jasperreportTemplateTypeId;

    private String jasperreportTemplateTypeJasperreportTemplateTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJasperreportTemplateName() {
        return jasperreportTemplateName;
    }

    public void setJasperreportTemplateName(String jasperreportTemplateName) {
        this.jasperreportTemplateName = jasperreportTemplateName;
    }

    public String getJasperreportTemplateFileName() {
        return jasperreportTemplateFileName;
    }

    public void setJasperreportTemplateFileName(String jasperreportTemplateFileName) {
        this.jasperreportTemplateFileName = jasperreportTemplateFileName;
    }

    public byte[] getJasperreportTemplateFile() {
        return jasperreportTemplateFile;
    }

    public void setJasperreportTemplateFile(byte[] jasperreportTemplateFile) {
        this.jasperreportTemplateFile = jasperreportTemplateFile;
    }

    public String getJasperreportTemplateFileContentType() {
        return jasperreportTemplateFileContentType;
    }

    public void setJasperreportTemplateFileContentType(String jasperreportTemplateFileContentType) {
        this.jasperreportTemplateFileContentType = jasperreportTemplateFileContentType;
    }

    public Instant getCorrespondentBillDate() {
        return correspondentBillDate;
    }

    public void setCorrespondentBillDate(Instant correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
    }

    public Boolean isIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getJasperreportTemplateTypeId() {
        return jasperreportTemplateTypeId;
    }

    public void setJasperreportTemplateTypeId(Long jasperreportTemplateTypeId) {
        this.jasperreportTemplateTypeId = jasperreportTemplateTypeId;
    }

    public String getJasperreportTemplateTypeJasperreportTemplateTypeName() {
        return jasperreportTemplateTypeJasperreportTemplateTypeName;
    }

    public void setJasperreportTemplateTypeJasperreportTemplateTypeName(String jasperreportTemplateTypeJasperreportTemplateTypeName) {
        this.jasperreportTemplateTypeJasperreportTemplateTypeName = jasperreportTemplateTypeJasperreportTemplateTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JasperreportTemplateDTO jasperreportTemplateDTO = (JasperreportTemplateDTO) o;
        if(jasperreportTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jasperreportTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JasperreportTemplateDTO{" +
            "id=" + getId() +
            ", jasperreportTemplateName='" + getJasperreportTemplateName() + "'" +
            ", jasperreportTemplateFileName='" + getJasperreportTemplateFileName() + "'" +
            ", jasperreportTemplateFile='" + getJasperreportTemplateFile() + "'" +
            ", correspondentBillDate='" + getCorrespondentBillDate() + "'" +
            ", isUse='" + isIsUse() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
