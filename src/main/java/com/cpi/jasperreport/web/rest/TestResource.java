/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestResource
 * Author:   admin
 * Date:     2018/4/26 9:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.jasperreport.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.jasperreport.repository.common.PortRepository;
import com.cpi.jasperreport.service.JasperReportService;
import com.cpi.jasperreport.utility.JasperReportUtility;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/4/26
 * @since 1.0.0
 */

@RestController
@RequestMapping("/api/test")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Autowired
    private JasperReportService jasperReportService;

    @Autowired
    private PortRepository portRepository;

    @GetMapping("/pdf")
    @Timed
    public ResponseEntity<byte[]> processPDF()  {
        log.debug("REST request to upload excel xls file for parse ");

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.putAll(JasperReportUtility.addImageToParameter("reports", "cherry.jpg", "cherryImage"));
        byte[] body = jasperReportService.exportBatchPDF("Test.jasper", parameter, new JREmptyDataSource());

        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=11.pdf");

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @GetMapping("/ports")
    @Timed
    public ResponseEntity<byte[]> processPortsPDF()  {
        log.debug("REST request to upload excel xls file for parse ");

        Map<String, Object> parameter = new HashMap<String, Object>();
//        parameter.putAll(JasperReportUtility.addImageToParameter("reports", "cherry.jpg", "cherryImage"));

        List<Object> lists = portRepository.findPorts();
        JRBeanArrayDataSource dataSource =
            new JRBeanArrayDataSource(lists.toArray());

        byte[] body = jasperReportService.exportBatchPDF("Correspondent.jasper", parameter, dataSource);


        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition","attachment;filename=11.pdf");

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

//
//    @GetMapping("/pdfs")
//    @Timed
//    public ResponseEntity<byte[]> processPDFs()  {
//        log.debug("REST request to upload excel xls file for parse ");
//        byte[] body = null;
//        StringBuilder path = new StringBuilder().append("reports/Test.jasper");
//        ClassPathResource classPathResource = new ClassPathResource(path.toString());
//        Map<String, Object> parameter = new HashMap<String, Object>();
//        try {
//            File file = classPathResource.getFile();
//            List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
//            JasperPrint jasperPrint1 = JasperFillManager.fillReport(new FileInputStream(file), parameter, new JREmptyDataSource());
//            JasperPrint jasperPrint2 = JasperFillManager.fillReport(new FileInputStream(file), parameter, new JREmptyDataSource());
//
//            jasperPrints.add(jasperPrint1);
//            jasperPrints.add(jasperPrint2);
//
////            body = jasperReportService.exportSimplePDF1(jasperPrint);
//            body = jasperReportService.exportBatchPDF(jasperPrints);
//
//        } catch (JRException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        HttpHeaders headers=new HttpHeaders();
//        headers.add("Content-Disposition","attachment;filename=11.pdf");
//
//        return new ResponseEntity<>(body, headers, HttpStatus.OK);
//    }

//        try (InputStream inputStream = TestResource.class.getClassLoader().getResourceAsStream("reports/cherry.jpg")) {
//            parameter.put("cherryImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(inputStream))));
//        } catch (JRException | IOException e) {
//            throw new RuntimeException("Failed to load images", e);
//        }

//    private
//    @GetMapping("/download-1")
//    @Timed
//    public ResponseEntity<byte[]> downloadFile1() throws IOException {
//        byte[] body = null;
//
//        try {
//            File file = ResourceUtils.getFile("classpath:test.xls");
//            FileInputStream excelFile = new FileInputStream(file);
//            body = new byte[excelFile.available()];
//            excelFile.read(body);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        HttpHeaders headers=new HttpHeaders();
//        headers.add("Content-Disposition","attachment;filename=11.xls");
//
//        return new ResponseEntity<>(body, headers, HttpStatus.OK);
//    }
//
//    @GetMapping("/download-2")
//    @Timed
//    public ResponseEntity<ByteArrayResource> downloadFile2() throws IOException {
//        File file = ResourceUtils.getFile("classpath:test.xls");
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//        HttpHeaders headers=new HttpHeaders();
//        headers.add("Content-Disposition","attachment;filename=11.xls");
//
//        return ResponseEntity.ok()
//            .headers(headers)
//            .contentLength(file.length())
//            .contentType(MediaType.parseMediaType("application/octet-stream"))
//            .body(resource);
//    }

}
