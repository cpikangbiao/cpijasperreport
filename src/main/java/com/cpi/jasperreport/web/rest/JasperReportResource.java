/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JasperReportResource
 * Author:   admin
 * Date:     2018/5/14 8:36
 * Description: Jasper Report Resource
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.jasperreport.web.rest;

import com.cpi.jasperreport.service.utility.JasperReportUtility;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈Jasper Report Resource〉
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */

@RestController
@RequestMapping("/api/jasperreport")
public class JasperReportResource {
    private final Logger log = LoggerFactory.getLogger(JasperReportResource.class);

    @Autowired
    private JasperReportUtility jasperReportUtility;

    @PostMapping("/pdf-withfile")
    
    public ResponseEntity<byte[]> processPDF(@RequestParam(value = "filename", required = true)  String jasperFileName,
                                             @RequestBody Map<String, Object> parameters)  {
        log.debug("REST request to process PDF file byte [] jasperFileName： {} parameters ：{} ", jasperFileName, parameters);

        byte[] body = jasperReportUtility.exportBatchPDF(jasperFileName, parameters, getJRDataSource(parameters));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/pdf-withid")
    
    public ResponseEntity<byte[]> processPDF(@RequestParam(value = "typeid", required = true)  Integer typeid,
                                             @RequestBody Map<String, Object> parameters)  {
        log.debug("REST request to process PDF file byte [] typeid： {} parameters ：{} ", typeid, parameters);

        byte[] body = jasperReportUtility.exportBatchPDF(Long.valueOf(typeid), parameters, getJRDataSource(parameters));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/addimage")
    
    public Map<String, Object> addImageMapParamete(@RequestParam(value = "path", required = true)  String path,
                                             @RequestParam(value = "imageFileName", required = true)  String imageFileName,
                                             @RequestParam(value = "imageParameterName", required = true)  String imageParameterName)  {
        log.debug("REST request to process PDF file byte [] ");

        return JasperReportUtility.addImageToParameter(path, imageFileName, imageParameterName);
    }


    @PostMapping("/html-withfile")
    
    public ResponseEntity<byte[]> processHTML(@RequestParam(value = "filename", required = true)  String jasperFileName,
                                             @RequestBody Map<String, Object> parameters)  {
        log.debug("REST request to process HTML file byte [] ");

        byte[] body = jasperReportUtility.exportBatchHTML(jasperFileName, parameters, getJRDataSource(parameters));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/word-withfile")
    
    public ResponseEntity<byte[]> processWord(@RequestParam(value = "filename", required = true)  String jasperFileName,
                                              @RequestBody Map<String, Object> parameters)  {
        log.debug("REST request to process HTML file byte [] jasperFileName： {} parameters ：{} ", jasperFileName, parameters);

        byte[] body = jasperReportUtility.exportBatchWord(jasperFileName, parameters, getJRDataSource(parameters));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

//    @PostMapping("/pdf-nodatasource")
//    
//    public ResponseEntity<byte[]> processPDFNoDataSource(@RequestParam(value = "filename", required = true)  String jasperFileName,
//                                             @RequestParam(value = "name", required = true)  Map<String, Object> parameters)  {
//        log.debug("REST request to process PDF file byte [] ");
//        byte[] body = jasperReportUtility.exportBatchPDF(jasperFileName, parameters, new JREmptyDataSource());
//
//        return new ResponseEntity<>(body, HttpStatus.OK);
//    }

    private JRDataSource getJRDataSource(Map<String, Object> parameters) {
        JRDataSource dataSource = new JREmptyDataSource();

        if (parameters.containsKey("datasource")) {
            try {
                List data = (ArrayList) parameters.get("datasource");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.toString().getBytes());
                dataSource = new JsonDataSource(byteArrayInputStream) ;
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
//        if (parameters.containsKey("JRDataSource")) {
//            dataSource = (JRBeanArrayDataSource) parameters.get("JRDataSource") ;
//        }
//
//        if (parameters.containsKey("JsonDataSource")) {
//            dataSource = (JsonDataSource) parameters.get("JsonDataSource") ;
//        }

        return dataSource;
    }
}
