package com.cpi.jasperreport.service.ext;


import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.domain.JasperreportTemplateType_;
import com.cpi.jasperreport.domain.JasperreportTemplate_;
import com.cpi.jasperreport.repository.JasperreportTemplateRepository;
import com.cpi.jasperreport.service.dto.JasperreportTemplateCriteria;
import com.cpi.jasperreport.service.dto.JasperreportTemplateDTO;
import com.cpi.jasperreport.service.mapper.JasperreportTemplateMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * Service for executing complex queries for JasperreportTemplate entities in the database.
 * The main input is a {@link JasperreportTemplateCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JasperreportTemplateDTO} or a {@link Page} of {@link JasperreportTemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional
public class JasperreportTemplateExtService extends QueryService<JasperreportTemplate> {

    private final Logger log = LoggerFactory.getLogger(JasperreportTemplateExtService.class);


    private final JasperreportTemplateRepository jasperreportTemplateRepository;

    private final JasperreportTemplateMapper jasperreportTemplateMapper;

    public JasperreportTemplateExtService(JasperreportTemplateRepository jasperreportTemplateRepository, JasperreportTemplateMapper jasperreportTemplateMapper) {
        this.jasperreportTemplateRepository = jasperreportTemplateRepository;
        this.jasperreportTemplateMapper = jasperreportTemplateMapper;
    }

    @Transactional(readOnly = true)
    public void deployJasperreportTemplate(Long jasperreportTemplateId) {
        log.debug("find by jasperreportTemplateId : {}", jasperreportTemplateId);
        StringBuilder jasperFilePath = new StringBuilder().append("reports/");
        ClassPathResource jasperFilePathResource = new ClassPathResource(jasperFilePath.toString());

        JasperreportTemplate jasperreportTemplate = jasperreportTemplateRepository.findOne(jasperreportTemplateId);

        if (jasperreportTemplate != null) {

            if (jasperreportTemplate.getJasperreportTemplateFile() != null) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(jasperreportTemplate.getJasperreportTemplateFile());
                ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                try {
                    ZipEntry zipEntry = zipInputStream.getNextEntry();
                    // iterates over entries in the zip file
                    while (zipEntry != null) {
                        String filePath = jasperFilePathResource.getPath() + File.separator + zipEntry.getName();
                        if (!zipEntry.isDirectory()) {
                            // if the entry is a file, extracts it
                            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                            byte[] bytesIn = new byte[BUFFER_SIZE];
                            int read = 0;
                            while ((read = zipInputStream.read(bytesIn)) != -1) {
                                outputStream.write(bytesIn, 0, read);
                            }
                            outputStream.close();
                        } else {
                            // if the entry is a directory, make the directory
                            File directory = new File(filePath);
                            directory.mkdir();
                        }
                        zipInputStream.closeEntry();
                        zipEntry = zipInputStream.getNextEntry();
                    }
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }



}
