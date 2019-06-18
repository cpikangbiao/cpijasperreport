package com.cpi.jasperreport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.jasperreport.service.JasperreportTemplateTypeService;
import com.cpi.jasperreport.web.rest.errors.BadRequestAlertException;
import com.cpi.jasperreport.web.rest.util.HeaderUtil;
import com.cpi.jasperreport.web.rest.util.PaginationUtil;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeDTO;
import com.cpi.jasperreport.service.dto.JasperreportTemplateTypeCriteria;
import com.cpi.jasperreport.service.JasperreportTemplateTypeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JasperreportTemplateType.
 */
@RestController
@RequestMapping("/api")
public class JasperreportTemplateTypeResource {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateTypeResource.class);

    private static final String ENTITY_NAME = "jasperreportTemplateType";

    private final JasperreportTemplateTypeService jasperreportTemplateTypeService;

    private final JasperreportTemplateTypeQueryService jasperreportTemplateTypeQueryService;

    public JasperreportTemplateTypeResource(JasperreportTemplateTypeService jasperreportTemplateTypeService, JasperreportTemplateTypeQueryService jasperreportTemplateTypeQueryService) {
        this.jasperreportTemplateTypeService = jasperreportTemplateTypeService;
        this.jasperreportTemplateTypeQueryService = jasperreportTemplateTypeQueryService;
    }

    /**
     * POST  /jasperreport-template-types : Create a new jasperreportTemplateType.
     *
     * @param jasperreportTemplateTypeDTO the jasperreportTemplateTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jasperreportTemplateTypeDTO, or with status 400 (Bad Request) if the jasperreportTemplateType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jasperreport-template-types")
    @Timed
    public ResponseEntity<JasperreportTemplateTypeDTO> createJasperreportTemplateType(@Valid @RequestBody JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO) throws URISyntaxException {
        log.debug("REST request to save JasperreportTemplateType : {}", jasperreportTemplateTypeDTO);
        if (jasperreportTemplateTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new jasperreportTemplateType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JasperreportTemplateTypeDTO result = jasperreportTemplateTypeService.save(jasperreportTemplateTypeDTO);
        return ResponseEntity.created(new URI("/api/jasperreport-template-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jasperreport-template-types : Updates an existing jasperreportTemplateType.
     *
     * @param jasperreportTemplateTypeDTO the jasperreportTemplateTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jasperreportTemplateTypeDTO,
     * or with status 400 (Bad Request) if the jasperreportTemplateTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the jasperreportTemplateTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jasperreport-template-types")
    @Timed
    public ResponseEntity<JasperreportTemplateTypeDTO> updateJasperreportTemplateType(@Valid @RequestBody JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO) throws URISyntaxException {
        log.debug("REST request to update JasperreportTemplateType : {}", jasperreportTemplateTypeDTO);
        if (jasperreportTemplateTypeDTO.getId() == null) {
            return createJasperreportTemplateType(jasperreportTemplateTypeDTO);
        }
        JasperreportTemplateTypeDTO result = jasperreportTemplateTypeService.save(jasperreportTemplateTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jasperreportTemplateTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jasperreport-template-types : get all the jasperreportTemplateTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of jasperreportTemplateTypes in body
     */
    @GetMapping("/jasperreport-template-types")
    @Timed
    public ResponseEntity<List<JasperreportTemplateTypeDTO>> getAllJasperreportTemplateTypes(JasperreportTemplateTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JasperreportTemplateTypes by criteria: {}", criteria);
        Page<JasperreportTemplateTypeDTO> page = jasperreportTemplateTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jasperreport-template-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jasperreport-template-types/:id : get the "id" jasperreportTemplateType.
     *
     * @param id the id of the jasperreportTemplateTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jasperreportTemplateTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jasperreport-template-types/{id}")
    @Timed
    public ResponseEntity<JasperreportTemplateTypeDTO> getJasperreportTemplateType(@PathVariable Long id) {
        log.debug("REST request to get JasperreportTemplateType : {}", id);
        JasperreportTemplateTypeDTO jasperreportTemplateTypeDTO = jasperreportTemplateTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jasperreportTemplateTypeDTO));
    }

    /**
     * DELETE  /jasperreport-template-types/:id : delete the "id" jasperreportTemplateType.
     *
     * @param id the id of the jasperreportTemplateTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jasperreport-template-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteJasperreportTemplateType(@PathVariable Long id) {
        log.debug("REST request to delete JasperreportTemplateType : {}", id);
        jasperreportTemplateTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
