package com.cpi.jasperreport.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A JasperreportTemplateType.
 */
@Entity
@Table(name = "jasperreport_template_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JasperreportTemplateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jasperreport_template_type_name", nullable = false)
    private String jasperreportTemplateTypeName;

    @NotNull
    @Column(name = "sort_num", nullable = false)
    private Integer sortNum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJasperreportTemplateTypeName() {
        return jasperreportTemplateTypeName;
    }

    public JasperreportTemplateType jasperreportTemplateTypeName(String jasperreportTemplateTypeName) {
        this.jasperreportTemplateTypeName = jasperreportTemplateTypeName;
        return this;
    }

    public void setJasperreportTemplateTypeName(String jasperreportTemplateTypeName) {
        this.jasperreportTemplateTypeName = jasperreportTemplateTypeName;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public JasperreportTemplateType sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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
        JasperreportTemplateType jasperreportTemplateType = (JasperreportTemplateType) o;
        if (jasperreportTemplateType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jasperreportTemplateType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JasperreportTemplateType{" +
            "id=" + getId() +
            ", jasperreportTemplateTypeName='" + getJasperreportTemplateTypeName() + "'" +
            ", sortNum=" + getSortNum() +
            "}";
    }
}
