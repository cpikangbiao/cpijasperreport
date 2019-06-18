package com.cpi.jasperreport.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cpi.jasperreport.domain.JasperreportTemplate} entity. This class is used
 * in {@link com.cpi.jasperreport.web.rest.JasperreportTemplateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jasperreport-templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JasperreportTemplateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter jasperreportTemplateName;

    private StringFilter jasperreportTemplateFileName;

    private InstantFilter correspondentBillDate;

    private BooleanFilter isUse;

    private IntegerFilter version;

    private LongFilter jasperreportTemplateTypeId;

    public JasperreportTemplateCriteria(){
    }

    public JasperreportTemplateCriteria(JasperreportTemplateCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.jasperreportTemplateName = other.jasperreportTemplateName == null ? null : other.jasperreportTemplateName.copy();
        this.jasperreportTemplateFileName = other.jasperreportTemplateFileName == null ? null : other.jasperreportTemplateFileName.copy();
        this.correspondentBillDate = other.correspondentBillDate == null ? null : other.correspondentBillDate.copy();
        this.isUse = other.isUse == null ? null : other.isUse.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.jasperreportTemplateTypeId = other.jasperreportTemplateTypeId == null ? null : other.jasperreportTemplateTypeId.copy();
    }

    @Override
    public JasperreportTemplateCriteria copy() {
        return new JasperreportTemplateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getJasperreportTemplateName() {
        return jasperreportTemplateName;
    }

    public void setJasperreportTemplateName(StringFilter jasperreportTemplateName) {
        this.jasperreportTemplateName = jasperreportTemplateName;
    }

    public StringFilter getJasperreportTemplateFileName() {
        return jasperreportTemplateFileName;
    }

    public void setJasperreportTemplateFileName(StringFilter jasperreportTemplateFileName) {
        this.jasperreportTemplateFileName = jasperreportTemplateFileName;
    }

    public InstantFilter getCorrespondentBillDate() {
        return correspondentBillDate;
    }

    public void setCorrespondentBillDate(InstantFilter correspondentBillDate) {
        this.correspondentBillDate = correspondentBillDate;
    }

    public BooleanFilter getIsUse() {
        return isUse;
    }

    public void setIsUse(BooleanFilter isUse) {
        this.isUse = isUse;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public LongFilter getJasperreportTemplateTypeId() {
        return jasperreportTemplateTypeId;
    }

    public void setJasperreportTemplateTypeId(LongFilter jasperreportTemplateTypeId) {
        this.jasperreportTemplateTypeId = jasperreportTemplateTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JasperreportTemplateCriteria that = (JasperreportTemplateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(jasperreportTemplateName, that.jasperreportTemplateName) &&
            Objects.equals(jasperreportTemplateFileName, that.jasperreportTemplateFileName) &&
            Objects.equals(correspondentBillDate, that.correspondentBillDate) &&
            Objects.equals(isUse, that.isUse) &&
            Objects.equals(version, that.version) &&
            Objects.equals(jasperreportTemplateTypeId, that.jasperreportTemplateTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        jasperreportTemplateName,
        jasperreportTemplateFileName,
        correspondentBillDate,
        isUse,
        version,
        jasperreportTemplateTypeId
        );
    }

    @Override
    public String toString() {
        return "JasperreportTemplateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (jasperreportTemplateName != null ? "jasperreportTemplateName=" + jasperreportTemplateName + ", " : "") +
                (jasperreportTemplateFileName != null ? "jasperreportTemplateFileName=" + jasperreportTemplateFileName + ", " : "") +
                (correspondentBillDate != null ? "correspondentBillDate=" + correspondentBillDate + ", " : "") +
                (isUse != null ? "isUse=" + isUse + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (jasperreportTemplateTypeId != null ? "jasperreportTemplateTypeId=" + jasperreportTemplateTypeId + ", " : "") +
            "}";
    }

}
