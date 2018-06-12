/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JasperReportService
 * Author:   admin
 * Date:     2018/5/10 11:07
 * Description: for Jasper report Service
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.jasperreport.service;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.cpi.jasperreport.utility.JasperReportUtility;
import com.cpi.jasperreport.web.rest.TestResource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈for Jasper report Service〉
 *
 * @author admin
 * @create 2018/5/10
 * @since 1.0.0
 */
@Service
public class JasperReportService {

    private final Logger log = LoggerFactory.getLogger(JasperReportService.class);

    public final byte[] exportBatchPDF(String jasperFileName, Map<String, Object> parameter, JRDataSource dataSource) {
        byte[] body = null;
        StringBuilder path = new StringBuilder().append("reports/").append(jasperFileName);
        ClassPathResource classPathResource = new ClassPathResource(path.toString());
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.putAll(parameter);
//        parameter.putAll(JasperReportUtility.addImageToParameter("reports", "cherry.jpg", "cherryImage"));
        try {
            File file = classPathResource.getFile();
            JasperPrint jasperPrint = JasperFillManager.fillReport(new FileInputStream(file), parameter, dataSource);
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

//    public final JasperReport compileReportFile(String jrxmlFileName) {
//        StringBuilder path = new StringBuilder().append("reports/").append(jrxmlFileName);
//        JasperReport jasperReport = null;
//        try {
//            ClassPathResource classPathResource = new ClassPathResource(path.toString());
//            InputStream employeeReportStream = classPathResource.getInputStream();
//            jasperReport
//                = JasperCompileManager.compileReport(employeeReportStream);
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        } catch (JRException e) {
//            e.printStackTrace();
//        }
//
//        return jasperReport;
//    }

//    public final void compileAndSaveReportFile(String jrxmlFileName) {
//        JasperReport jasperReport = compileReportFile(jrxmlFileName);
//        StringBuilder path = new StringBuilder()
//                                    .append("reports/")
//                                    .append(jrxmlFileName.substring(0, jrxmlFileName.lastIndexOf(".")))
//                                    .append("jasper");
//        if (jasperReport != null) {
//            try {
//                ClassPathResource classPathResource = new ClassPathResource(path.toString());
//
//                InputStream inputStream = classPathResource.
//                File somethingFile = File.createTempFile("test", ".txt");
//                JRSaver..saveObject().saveObject(jasperReport, fileName);
//                try {
//                    FileUtils.copyInputStreamToFile(inputStream, somethingFile);
//                } finally {
//                    IOUtils.closeQuietly(inputStream);
//                }
//
//
//            } catch (JRException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public final void exporterHTML(JasperReport jasperReport) {
//        HtmlExporter exporter = new HtmlExporter();
//
//// Set input ...
//        exporter.setExporterOutput(
//            new SimpleHtmlExporterOutput("employeeReport.html"));
//
//        exporter.exportReport();
//    }
//
//    public final void exporterCSV(JasperReport jasperReport) {
//        JRCsvExporter exporter = new JRCsvExporter();
//
//// Set input ...
//        exporter.setExporterOutput(
//            new SimpleWriterExporterOutput("employeeReport.csv"));
//
//        exporter.exportReport();
//    }
//
//    public final void exporterXLS(JasperReport jasperReport) {
//        JRXlsxExporter exporter = new JRXlsxExporter();
//
//// Set input and output ...
//        SimpleXlsxReportConfiguration reportConfig
//            = new SimpleXlsxReportConfiguration();
//        reportConfig.setSheetNames(new String[] { "Employee Data" });
//
//        exporter.setConfiguration(reportConfig);
//        exporter.exportReport();
//    }

//    public final byte[] exportSimplePDF(JasperPrint jasperPrint) {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        byte[] body = null;
//
//        try {
//            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
//            body = output.toByteArray();
//        } catch (JRException e) {
//            e.printStackTrace();
//        }
//
//        return body;
//    }


}
