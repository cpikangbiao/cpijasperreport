package com.cpi.jasperreport.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the JasperreportTemplateType entity. This class is used in JasperreportTemplateTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /jasperreport-template-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JasperreportTemplateTypeCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter jasperreportTemplateTypeName;

    private IntegerFilter sortNum;

    public JasperreportTemplateTypeCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getJasperreportTemplateTypeName() {
        return jasperreportTemplateTypeName;
    }

    public void setJasperreportTemplateTypeName(StringFilter jasperreportTemplateTypeName) {
        this.jasperreportTemplateTypeName = jasperreportTemplateTypeName;
    }

    public IntegerFilter getSortNum() {
        return sortNum;
    }

    public void setSortNum(IntegerFilter sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "JasperreportTemplateTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (jasperreportTemplateTypeName != null ? "jasperreportTemplateTypeName=" + jasperreportTemplateTypeName + ", " : "") +
                (sortNum != null ? "sortNum=" + sortNum + ", " : "") +
            "}";
    }

}
