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
package com.cpi.jasperreport.utility;

import com.cpi.jasperreport.web.rest.TestResource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/5/11
 * @since 1.0.0
 */
public class JasperReportUtility {

    private static final Logger log = LoggerFactory.getLogger(JasperReportUtility.class);

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

}
