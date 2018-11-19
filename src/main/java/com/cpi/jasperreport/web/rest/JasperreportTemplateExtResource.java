package com.cpi.jasperreport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.jasperreport.service.JasperreportTemplateExtService;
import com.cpi.jasperreport.service.JasperreportTemplateQueryService;
import com.cpi.jasperreport.service.JasperreportTemplateService;
import com.cpi.jasperreport.service.dto.JasperreportTemplateCriteria;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.web.rest.errors.BadRequestAlertException;
import com.cpi.jasperreport.web.rest.util.HeaderUtil;
import com.cpi.jasperreport.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JasperreportTemplate.
 */
@RestController
@RequestMapping("/api")
public class JasperreportTemplateExtResource {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateExtResource.class);

    private static final String ENTITY_NAME = "jasperreportTemplate";

    @Autowired
    private JasperreportTemplateExtService jasperreportTemplateExtService;

    @PutMapping("/jasperreport-templates/{id}/deploy")
    @Timed
    public ResponseEntity<Void> deployJasperreportTemplate(@PathVariable Long id) {
        log.debug("REST request to deploy Jasperreport Template : {}", id);
        jasperreportTemplateExtService.deployJasperreportTemplate(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeployAlert(ENTITY_NAME, id.toString())).build();
    }
}
