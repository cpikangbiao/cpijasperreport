package com.cpi.jasperreport.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.cpi.jasperreport")
public class FeignConfiguration {

}
