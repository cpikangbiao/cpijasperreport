package com.cpi.jasperreport.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the JasperreportTemplate entity. This class is used in JasperreportTemplateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /jasperreport-templates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JasperreportTemplateCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter jasperreportTemplateName;

    private InstantFilter correspondentBillDate;

    private BooleanFilter isUse;

    private IntegerFilter version;

    private LongFilter jasperreportTemplateTypeId;

    public JasperreportTemplateCriteria() {
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
    public String toString() {
        return "JasperreportTemplateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (jasperreportTemplateName != null ? "jasperreportTemplateName=" + jasperreportTemplateName + ", " : "") +
                (correspondentBillDate != null ? "correspondentBillDate=" + correspondentBillDate + ", " : "") +
                (isUse != null ? "isUse=" + isUse + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (jasperreportTemplateTypeId != null ? "jasperreportTemplateTypeId=" + jasperreportTemplateTypeId + ", " : "") +
            "}";
    }

}
