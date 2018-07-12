package com.cpi.jasperreport.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A JasperreportTemplate.
 */
@Entity
@Table(name = "jasperreport_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JasperreportTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jasperreport_template_name")
    private String jasperreportTemplateName;

    @Column(name = "jasperreport_template_file_name")
    private String jasperreportTemplateFileName;

    @Lob
    @Column(name = "jasperreport_template_file")
    private byte[] jasperreportTemplateFile;

    @Column(name = "jasperreport_template_file_content_type")
    private String jasperreportTemplateFileContentType;

    @Column(name = "correspondent_bill_date")
    private Instant correspondentBillDate;

    @Column(name = "is_use")
    private Boolean isUse;

    @Column(name = "version")
    private Integer version;

    @ManyToOne
    private JasperreportTemplateType jasperreportTemplateType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJasperreportTemplateName() {
        return jasperreportTemplateName;
    }

    public JasperreportTemplate jasperreportTemplateName(String jasperreportTemplateName) {
        this.jasperreportTemplateName = jasperreportTemplateName;
        return this;
    }

    public void setJasperreportTemplateName(String jasperreportTemplateName) {
        this.jasperreportTemplateName = jasperreportTemplateName;
    }

    public String getJasperreportTemplateFileName() {
        return jasperreportTemplateFileName;
    }

    public JasperreportTemplate jasperreportTemplateFileName(String jasperreportTemplateFileName) {
        this.jasperreportTemplateFileName = jasperreportTemplateFileName;
        return this;
    }

    public void setJasperreportTemplateFileName(String jasperreportTemplateFileName) {
        this.jasperreportTemplateFileName = jasperreportTemplateFileName;
    }

    public byte[] getJasperreportTemplateFile() {
        return jasperreportTemplateFile;
    }

    public JasperreportTemplate jasperreportTemplateFile(byte[] jasperreportTemplateFile) {
        this.jasperreportTemplateFile = jasperreportTemplateFile;
        return this;
    }

    public void setJasperreportTemplateFile(byte[] jasperreportTemplateFile) {
        this.jasperreportTemplateFile = jasperreportTemplateFile;
    }

    public String getJasperreportTemplateFileContentType() {
        return jasperreportTemplateFileContentType;
    }

    public JasperreportTemplate jasperreportTemplateFileContentType(String jasperreportTemplateFileContentType) {
        this.jasperreportTemplateFileContentType = jasperreportTemplateFileContentType;
        return this;
    }

    public void setJasperreportTemplateFileContentType(String jasperreportTemplateFileContentType) {
        this.jasperreportTemplateFileContentType = jasperreportTemplateFileContentType;
    }

    public Instant getCorrespondentBillDate() {
        return correspondentBillDate;
    }

    public JasperreportTemplate correspondentBillDate(Instant correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
        return this;
    }

    public void setCorrespondentBillDate(Instant correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
    }

    public Boolean isIsUse() {
        return isUse;
    }

    public JasperreportTemplate isUse(Boolean isUse) {
        this.isUse = isUse;
        return this;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    public Integer getVersion() {
        return version;
    }

    public JasperreportTemplate version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public JasperreportTemplateType getJasperreportTemplateType() {
        return jasperreportTemplateType;
    }

    public JasperreportTemplate jasperreportTemplateType(JasperreportTemplateType jasperreportTemplateType) {
        this.jasperreportTemplateType = jasperreportTemplateType;
        return this;
    }

    public void setJasperreportTemplateType(JasperreportTemplateType jasperreportTemplateType) {
        this.jasperreportTemplateType = jasperreportTemplateType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JasperreportTemplate jasperreportTemplate = (JasperreportTemplate) o;
        if (jasperreportTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jasperreportTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JasperreportTemplate{" +
            "id=" + getId() +
            ", jasperreportTemplateName='" + getJasperreportTemplateName() + "'" +
            ", jasperreportTemplateFileName='" + getJasperreportTemplateFileName() + "'" +
            ", jasperreportTemplateFile='" + getJasperreportTemplateFile() + "'" +
            ", jasperreportTemplateFileContentType='" + getJasperreportTemplateFileContentType() + "'" +
            ", correspondentBillDate='" + getCorrespondentBillDate() + "'" +
            ", isUse='" + isIsUse() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
