package com.cpi.jasperreport.web.rest;

import com.cpi.jasperreport.service.JasperreportTemplateService;
import com.cpi.jasperreport.web.rest.errors.BadRequestAlertException;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.service.dto.JasperreportTemplateCriteria;
import com.cpi.jasperreport.service.JasperreportTemplateQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.jasperreport.domain.JasperreportTemplate}.
 */
@RestController
@RequestMapping("/api")
public class JasperreportTemplateResource {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateResource.class);

    private static final String ENTITY_NAME = "cpijasperreportJasperreportTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JasperreportTemplateService jasperreportTemplateService;

    private final JasperreportTemplateQueryService jasperreportTemplateQueryService;

    public JasperreportTemplateResource(JasperreportTemplateService jasperreportTemplateService, JasperreportTemplateQueryService jasperreportTemplateQueryService) {
        this.jasperreportTemplateService = jasperreportTemplateService;
        this.jasperreportTemplateQueryService = jasperreportTemplateQueryService;
    }

    /**
     * {@code POST  /jasperreport-templates} : Create a new jasperreportTemplate.
     *
     * @param jasperreportTemplateDTO the jasperreportTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jasperreportTemplateDTO, or with status {@code 400 (Bad Request)} if the jasperreportTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jasperreport-templates")
    public ResponseEntity<JasperreportTemplateDTO> createJasperreportTemplate(@RequestBody JasperreportTemplateDTO jasperreportTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save JasperreportTemplate : {}", jasperreportTemplateDTO);
        if (jasperreportTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new jasperreportTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JasperreportTemplateDTO result = jasperreportTemplateService.save(jasperreportTemplateDTO);
        return ResponseEntity.created(new URI("/api/jasperreport-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jasperreport-templates} : Updates an existing jasperreportTemplate.
     *
     * @param jasperreportTemplateDTO the jasperreportTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jasperreportTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the jasperreportTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jasperreportTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jasperreport-templates")
    public ResponseEntity<JasperreportTemplateDTO> updateJasperreportTemplate(@RequestBody JasperreportTemplateDTO jasperreportTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update JasperreportTemplate : {}", jasperreportTemplateDTO);
        if (jasperreportTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JasperreportTemplateDTO result = jasperreportTemplateService.save(jasperreportTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jasperreportTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jasperreport-templates} : get all the jasperreportTemplates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jasperreportTemplates in body.
     */
    @GetMapping("/jasperreport-templates")
    public ResponseEntity<List<JasperreportTemplateDTO>> getAllJasperreportTemplates(JasperreportTemplateCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get JasperreportTemplates by criteria: {}", criteria);
        Page<JasperreportTemplateDTO> page = jasperreportTemplateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /jasperreport-templates/count} : count all the jasperreportTemplates.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/jasperreport-templates/count")
    public ResponseEntity<Long> countJasperreportTemplates(JasperreportTemplateCriteria criteria) {
        log.debug("REST request to count JasperreportTemplates by criteria: {}", criteria);
        return ResponseEntity.ok().body(jasperreportTemplateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /jasperreport-templates/:id} : get the "id" jasperreportTemplate.
     *
     * @param id the id of the jasperreportTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jasperreportTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jasperreport-templates/{id}")
    public ResponseEntity<JasperreportTemplateDTO> getJasperreportTemplate(@PathVariable Long id) {
        log.debug("REST request to get JasperreportTemplate : {}", id);
        Optional<JasperreportTemplateDTO> jasperreportTemplateDTO = jasperreportTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jasperreportTemplateDTO);
    }

    /**
     * {@code DELETE  /jasperreport-templates/:id} : delete the "id" jasperreportTemplate.
     *
     * @param id the id of the jasperreportTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jasperreport-templates/{id}")
    public ResponseEntity<Void> deleteJasperreportTemplate(@PathVariable Long id) {
        log.debug("REST request to delete JasperreportTemplate : {}", id);
        jasperreportTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
