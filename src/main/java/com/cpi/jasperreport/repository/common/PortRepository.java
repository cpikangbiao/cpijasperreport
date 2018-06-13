/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CompanyRepository
 * Author:   admin
 * Date:     2018/3/30 10:31
 * Description: for cpicommon Micro Service
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.jasperreport.repository.common;

import com.cpi.jasperreport.client.AuthorizedFeignClient;
import com.cpi.jasperreport.config.LongTimeFeignConfiguration;
import com.hazelcast.core.Message;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈for cpicommon Micro Service〉
 *
 * @author admin
 * @create 2018/3/30
 * @since 1.0.0
 */
@AuthorizedFeignClient(name = "cpicommon", configuration = LongTimeFeignConfiguration.class) // , fallback = TicketClientHystrix.class)

public interface PortRepository {
//    @RequestMapping(value = "/api/teams/", method = RequestMethod.GET)
//    List<Team> findTeams();
//
    @RequestMapping(value = "/api/test/ports", method = RequestMethod.GET)
    @HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
    List findPorts();

//    private RestTemplate restTemplate = new RestTemplate();
//    ResponseDTO responseDTO = restTemplate.getForEntity("/api/endpoint", ResponseDTO.class).getBody();

//    @RequestMapping(method = RequestMethod.POST, value = "/create")
//    Message<String> create(
//        @RequestParam(value = "Type") Integer Type,
//        @RequestParam(value = "amount") String amount,
//        @RequestParam(value = "userId") String userId,
//        @RequestParam(value = "mobile") String mobile,
//        @RequestParam(value = "status") Integer status,
//        @RequestParam(value = "belong") Integer belong,
//        @RequestParam(value = "useProfit")String useProfit,
//        @RequestParam(value = "useCounter")String useCounter);
}
