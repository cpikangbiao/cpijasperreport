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

import com.codahale.metrics.annotation.Timed;
import com.cpi.jasperreport.web.utility.JasperReportUtility;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Timed
    public ResponseEntity<byte[]> processPDF(@RequestParam(value = "filename", required = true)  String jasperFileName,
                                             @RequestBody Map<String, Object> parameters)  {
        log.debug("REST request to process PDF file byte [] ");

        JRDataSource dataSource = new JREmptyDataSource();
        if (parameters.containsKey("datasource")) {
            dataSource = new JRBeanArrayDataSource(((ArrayList) parameters.get("datasource")).toArray()) ;
        }

        byte[] body = jasperReportUtility.exportBatchPDF(jasperFileName, parameters, dataSource);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/pdf-withid")
    @Timed
    public ResponseEntity<byte[]> processPDF(@RequestParam(value = "typeid", required = true)  Integer typeid,
                                             @RequestBody Map<String, Object> parameters)  {
        log.debug("REST request to process PDF file byte [] ");

        JRDataSource dataSource = new JREmptyDataSource();
        if (parameters.containsKey("datasource")) {
            dataSource = new JRBeanArrayDataSource(((ArrayList) parameters.get("datasource")).toArray()) ;
        }
        Long tempid = new Long(typeid);
        byte[] body = jasperReportUtility.exportBatchPDF(tempid, parameters, dataSource);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/addimage")
    @Timed
    public Map<String, Object> addImageMapParamete(@RequestParam(value = "path", required = true)  String path,
                                             @RequestParam(value = "imageFileName", required = true)  String imageFileName,
                                             @RequestParam(value = "imageParameterName", required = true)  String imageParameterName)  {
        log.debug("REST request to process PDF file byte [] ");

        return JasperReportUtility.addImageToParameter(path, imageFileName, imageParameterName);
    }


//    @PostMapping("/pdf-nodatasource")
//    @Timed
//    public ResponseEntity<byte[]> processPDFNoDataSource(@RequestParam(value = "filename", required = true)  String jasperFileName,
//                                             @RequestParam(value = "name", required = true)  Map<String, Object> parameters)  {
//        log.debug("REST request to process PDF file byte [] ");
//        byte[] body = jasperReportUtility.exportBatchPDF(jasperFileName, parameters, new JREmptyDataSource());
//
//        return new ResponseEntity<>(body, HttpStatus.OK);
//    }
}
