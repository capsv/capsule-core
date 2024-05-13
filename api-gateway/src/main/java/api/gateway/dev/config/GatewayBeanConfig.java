package api.gateway.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GatewayBeanConfig {

    @Bean
    @LoadBalanced
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080") //"lb://auth-service"
            .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                log.info("Sending request to URI: {}", clientRequest.url());
                return Mono.just(clientRequest);
            })).filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                log.info("Received response with status code: {}", clientResponse.statusCode());
                return Mono.just(clientResponse);
            })).build();
    }
}
