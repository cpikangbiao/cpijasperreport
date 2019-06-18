package com.cpi.jasperreport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.jasperreport.service.JasperreportTemplateService;
import com.cpi.jasperreport.web.rest.errors.BadRequestAlertException;
import com.cpi.jasperreport.web.rest.util.HeaderUtil;
import com.cpi.jasperreport.web.rest.util.PaginationUtil;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.service.dto.JasperreportTemplateCriteria;
import com.cpi.jasperreport.service.JasperreportTemplateQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JasperreportTemplateResource {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateResource.class);

    private static final String ENTITY_NAME = "jasperreportTemplate";

    private final JasperreportTemplateService jasperreportTemplateService;

    private final JasperreportTemplateQueryService jasperreportTemplateQueryService;

    public JasperreportTemplateResource(JasperreportTemplateService jasperreportTemplateService, JasperreportTemplateQueryService jasperreportTemplateQueryService) {
        this.jasperreportTemplateService = jasperreportTemplateService;
        this.jasperreportTemplateQueryService = jasperreportTemplateQueryService;
    }

    /**
     * POST  /jasperreport-templates : Create a new jasperreportTemplate.
     *
     * @param jasperreportTemplateDTO the jasperreportTemplateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jasperreportTemplateDTO, or with status 400 (Bad Request) if the jasperreportTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jasperreport-templates")
    @Timed
    public ResponseEntity<JasperreportTemplateDTO> createJasperreportTemplate(@RequestBody JasperreportTemplateDTO jasperreportTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save JasperreportTemplate : {}", jasperreportTemplateDTO);
        if (jasperreportTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new jasperreportTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JasperreportTemplateDTO result = jasperreportTemplateService.save(jasperreportTemplateDTO);
        return ResponseEntity.created(new URI("/api/jasperreport-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jasperreport-templates : Updates an existing jasperreportTemplate.
     *
     * @param jasperreportTemplateDTO the jasperreportTemplateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jasperreportTemplateDTO,
     * or with status 400 (Bad Request) if the jasperreportTemplateDTO is not valid,
     * or with status 500 (Internal Server Error) if the jasperreportTemplateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jasperreport-templates")
    @Timed
    public ResponseEntity<JasperreportTemplateDTO> updateJasperreportTemplate(@RequestBody JasperreportTemplateDTO jasperreportTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update JasperreportTemplate : {}", jasperreportTemplateDTO);
        if (jasperreportTemplateDTO.getId() == null) {
            return createJasperreportTemplate(jasperreportTemplateDTO);
        }
        JasperreportTemplateDTO result = jasperreportTemplateService.save(jasperreportTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jasperreportTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jasperreport-templates : get all the jasperreportTemplates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of jasperreportTemplates in body
     */
    @GetMapping("/jasperreport-templates")
    @Timed
    public ResponseEntity<List<JasperreportTemplateDTO>> getAllJasperreportTemplates(JasperreportTemplateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JasperreportTemplates by criteria: {}", criteria);
        Page<JasperreportTemplateDTO> page = jasperreportTemplateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jasperreport-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jasperreport-templates/:id : get the "id" jasperreportTemplate.
     *
     * @param id the id of the jasperreportTemplateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jasperreportTemplateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jasperreport-templates/{id}")
    @Timed
    public ResponseEntity<JasperreportTemplateDTO> getJasperreportTemplate(@PathVariable Long id) {
        log.debug("REST request to get JasperreportTemplate : {}", id);
        JasperreportTemplateDTO jasperreportTemplateDTO = jasperreportTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jasperreportTemplateDTO));
    }

    /**
     * DELETE  /jasperreport-templates/:id : delete the "id" jasperreportTemplate.
     *
     * @param id the id of the jasperreportTemplateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jasperreport-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteJasperreportTemplate(@PathVariable Long id) {
        log.debug("REST request to delete JasperreportTemplate : {}", id);
        jasperreportTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
