package profile.service.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProfileService {

    public static void main(String[] args) {
        SpringApplication.run(ProfileService.class, args);
    }

}
