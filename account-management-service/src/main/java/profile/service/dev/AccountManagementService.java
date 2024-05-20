package profile.service.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountManagementService {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagementService.class, args);
    }

}
