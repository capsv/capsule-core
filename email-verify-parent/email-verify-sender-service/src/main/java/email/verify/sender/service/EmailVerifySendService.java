package email.verify.sender.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailVerifySendService {

    public static void main(String[] args) {
        SpringApplication.run(EmailVerifySendService.class, args);
    }

}