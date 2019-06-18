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
package com.cpi.jasperreport.service.utility;

import com.cpi.jasperreport.domain.JasperreportTemplate;
import com.cpi.jasperreport.repository.JasperreportTemplateRepository;
import com.cpi.jasperreport.repository.JasperreportTemplateTypeRepository;
import com.cpi.jasperreport.web.rest.TestResource;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
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
@Service
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

    public final byte[] exportBatchHTML(String jasperFileName, Map<String, Object> parameter, JRDataSource dataSource) {
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
            body = exportSimpleHTML(jasperPrint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
        return body;
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

    public final byte[] exportBatchWord(String jasperFileName, Map<String, Object> parameter, JRDataSource dataSource) {
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
            body = exportSimpleWord(jasperPrint);
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

    public final byte[] exportSimpleHTML(JasperPrint jasperPrint) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        HtmlExporter exporter = new HtmlExporter();

        byte[] body = null;

        try {
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(output));
            exporter.exportReport();

            body = output.toByteArray();

        } catch (JRException e) {
            e.printStackTrace();
        }

        return body;
    }


    public final byte[] exportSimpleWord(JasperPrint jasperPrint) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRDocxExporter exporter = new JRDocxExporter ();

        byte[] body = null;

        try {
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
            exporter.setConfiguration(defaultDocxReportConfig());
            exporter.setConfiguration(defaultDocxExportConfig());

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

//        exportConfig.setEncrypted(true);
//        exportConfig.set128BitKey(true);
//        exportConfig.setUserPassword("jasper");
//        exportConfig.setOwnerPassword("reports");
//        exportConfig.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);

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

    private SimpleDocxReportConfiguration  defaultDocxReportConfig() {
        SimpleDocxReportConfiguration  docxReportConfiguration = new SimpleDocxReportConfiguration ();
//        docxReportConfiguration.setFlexibleRowHeight(true);
//        docxReportConfiguration.setFramesAsNestedTables();
        return docxReportConfiguration;
    }

    private SimpleDocxExporterConfiguration defaultDocxExportConfig() {
        SimpleDocxExporterConfiguration docxExporterConfiguration = new SimpleDocxExporterConfiguration();
        docxExporterConfiguration.setMetadataAuthor("CPI - China Shipowners Mutual Assurance Association");
//        docxExporterConfiguration.setMetadataTitle();

        return docxExporterConfiguration;
    }

    private SimpleXlsReportConfiguration    defaultXlsReportConfig() {
        SimpleXlsReportConfiguration   xlsReportConfiguration = new SimpleXlsReportConfiguration  ();
        xlsReportConfiguration.setOnePagePerSheet(true);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(false);
        xlsReportConfiguration.setDetectCellType(true);
        xlsReportConfiguration.setWhitePageBackground(false);

        return xlsReportConfiguration;
    }

    private SimpleXlsExporterConfiguration  defaultXlsExportConfig() {
        SimpleXlsExporterConfiguration xlsExporterConfiguration = new SimpleXlsExporterConfiguration();
        xlsExporterConfiguration.setMetadataAuthor("CPI - China Shipowners Mutual Assurance Association");


        return xlsExporterConfiguration;
    }
}

