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

/**
 * Criteria class for the {@link com.cpi.jasperreport.domain.JasperreportTemplateType} entity. This class is used
 * in {@link com.cpi.jasperreport.web.rest.JasperreportTemplateTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jasperreport-template-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JasperreportTemplateTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter jasperreportTemplateTypeName;

    private IntegerFilter sortNum;

    public JasperreportTemplateTypeCriteria(){
    }

    public JasperreportTemplateTypeCriteria(JasperreportTemplateTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.jasperreportTemplateTypeName = other.jasperreportTemplateTypeName == null ? null : other.jasperreportTemplateTypeName.copy();
        this.sortNum = other.sortNum == null ? null : other.sortNum.copy();
    }

    @Override
    public JasperreportTemplateTypeCriteria copy() {
        return new JasperreportTemplateTypeCriteria(this);
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JasperreportTemplateTypeCriteria that = (JasperreportTemplateTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(jasperreportTemplateTypeName, that.jasperreportTemplateTypeName) &&
            Objects.equals(sortNum, that.sortNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        jasperreportTemplateTypeName,
        sortNum
        );
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
