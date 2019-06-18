package com.cpi.jasperreport.web.rest.ext;

import com.codahale.metrics.annotation.Timed;
import com.cpi.jasperreport.service.ext.JasperreportTemplateExtService;
import com.cpi.jasperreport.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
