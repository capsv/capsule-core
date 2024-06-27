package org.capsule.com.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StatisticsMService {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsMService.class, args);
    }

}
