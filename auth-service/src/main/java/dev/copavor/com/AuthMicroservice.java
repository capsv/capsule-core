package dev.copavor.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(AuthMicroservice.class, args);
    }

}