/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JasperReportUtility
 * Author:   admin
 * Date:     2018/5/11 14:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.jasperreport.web.utility;

import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.domain.JasperreportTemplateType;
import com.cpi.jasperreport.repository.JasperreportTemplateRepository;
import com.cpi.jasperreport.repository.JasperreportTemplateTypeRepository;
import com.cpi.jasperreport.web.rest.JasperreportTemplateResource;
import com.cpi.jasperreport.web.rest.TestResource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/5/11
 * @since 1.0.0
 */
@Component
public class JasperReportUtility {

    public static JasperReportUtility jasperReportUtility;

    private static final Logger log = LoggerFactory.getLogger(JasperReportUtility.class);

    @Autowired
    private JasperreportTemplateRepository jasperreportTemplateRepository;

    @Autowired
    private JasperreportTemplateTypeRepository jasperreportTemplateTypeRepository;

    public JasperReportUtility() {
    }

    @PostConstruct
    public void init() {
        jasperReportUtility = this;
        jasperReportUtility.jasperreportTemplateRepository = this.jasperreportTemplateRepository;
        jasperReportUtility.jasperreportTemplateTypeRepository = this.jasperreportTemplateTypeRepository;
    }

    public static final Map<String, Object> addCPILogoBlueImage() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.putAll(JasperReportUtility.addImageToParameter("reports", "cpiblue.jpg", "cpiblueImage"));
        return parameters;
    }

    public static final Map<String, Object> addImageToParameter(String imageFilePath, String imageFileName, String parameterName) {
        log.debug("add Image path:{} image name: {} To Parameter ", imageFilePath, imageFileName);

        StringBuilder path = new StringBuilder().append(imageFilePath).append("/").append(imageFileName);
        Map<String, Object> parameters = new HashMap<String, Object>();
        try (InputStream inputStream = TestResource.class.getClassLoader().getResourceAsStream(path.toString())) {
            parameters.put(parameterName, ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(inputStream))));
        } catch (JRException | IOException e) {
            throw new RuntimeException("Failed to load images", e);
        }

        return parameters;
    }

    public final byte[] exportBatchPDF(String jasperFileName, Map<String, Object> parameter, JRDataSource dataSource) {
        byte[] body = null;
        StringBuilder imagepath = new StringBuilder().append("reports/");
        ClassPathResource imageclassPathResource = new ClassPathResource(imagepath.toString());
        parameter.put("reportDir", imageclassPathResource.getPath());

        parameter.put("SUBREPORT_DIR", imageclassPathResource.getPath());

        StringBuilder path = new StringBuilder().append("reports/").append(jasperFileName);
        ClassPathResource classPathResource = new ClassPathResource(path.toString());
        parameter.putAll(JasperReportUtility.addCPILogoBlueImage());



        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(classPathResource.getInputStream(), parameter, dataSource);
            body = exportSimplePDF(jasperPrint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
        return body;
    }

    public final byte[] exportBatchPDF(Long id, Map<String, Object> parameter, JRDataSource dataSource) {
        byte[] body = null;
        StringBuilder imagepath = new StringBuilder().append("reports/");
        ClassPathResource imageclassPathResource = new ClassPathResource(imagepath.toString());
        parameter.put("imagepath", imageclassPathResource.getPath());

        parameter.put("SUBREPORT_DIR", imageclassPathResource.getPath());

//        JasperreportTemplateType jasperreportTemplateType = jasperreportTemplateTypeRepository.findOne(id);
        JasperreportTemplate jasperreportTemplate = jasperreportTemplateRepository.findOne(id);//.findTopByIsUseTrueAndJasperreportTemplateTypeOrderByVersionDesc(jasperreportTemplateType);
        try {
            InputStream inputStream = null;

            if (jasperreportTemplate.getJasperreportTemplateFileName() != null) {
                StringBuilder path = new StringBuilder().append("reports/").append(jasperreportTemplate.getJasperreportTemplateFileName());
                ClassPathResource classPathResource = new ClassPathResource(path.toString());
                inputStream = new FileInputStream(classPathResource.getFile());
            } else {
                inputStream = new ByteArrayInputStream(jasperreportTemplate.getJasperreportTemplateFile()) ;
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameter, dataSource);
            body = exportSimplePDF(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public final byte[] exportSimplePDF(JasperPrint jasperPrint) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();

        byte[] body = null;

        try {
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
            exporter.setConfiguration(defaultReportConfig());
            exporter.setConfiguration(defaultExportConfig());

            exporter.exportReport();

            body = output.toByteArray();

        } catch (JRException e) {
            e.printStackTrace();
        }

        return body;
    }

    public final byte[] exportBatchPDF(List<JasperPrint> jasperPrints) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();

        byte[] body = null;

        try {
            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
            exporter.setConfiguration(defaultReportConfig());
            exporter.setConfiguration(defaultExportConfig());

            exporter.exportReport();

            body = output.toByteArray();

        } catch (JRException e) {
            e.printStackTrace();
        }

        return body;
    }

    private SimplePdfExporterConfiguration defaultExportConfig() {
        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("CPI - China Shipowners Mutual Assurance Association");
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");
        return exportConfig;
    }

    private SimplePdfReportConfiguration defaultReportConfig() {
        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);
        return reportConfig;
    }
}

