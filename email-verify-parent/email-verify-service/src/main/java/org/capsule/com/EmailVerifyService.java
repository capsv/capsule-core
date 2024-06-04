package org.capsule.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailVerifyService {

    public static void main(String[] args) {
        SpringApplication.run(EmailVerifyService.class, args);
    }

}
