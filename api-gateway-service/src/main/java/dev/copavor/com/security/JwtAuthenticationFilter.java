package dev.copavor.com.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().toString();


        if (requestPath.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // Для profile-service требуется проверка JWT
        if (requestPath.startsWith("/profile")) {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String jwtToken = authorizationHeader.substring(7);
            log.info(jwtToken);

            // TODO решить проблему почему к http://localhost:8080/auth/v1.0/api все отправляется нормально, а http://auth-service нет
            WebClient webClient = webClientBuilder.baseUrl("http://localhost:8080/auth/v1.0/api").build(); //http://auth-service or http://localhost:8080/auth/v1.0/api
            log.info("Sending validation request to auth-service with token: {}", jwtToken);
            Mono<Boolean> isValid = webClient
                    .mutate()
                    .build()
                    .post()
                    .uri("/validate")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue("{\"token\":\"" + jwtToken + "\"}")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .timeout(Duration.ofSeconds(5))
                    .onErrorReturn(false);

            isValid.subscribe(result -> log.info("**A** Received validation result: {} **A**", result));
            return isValid.flatMap(valid -> {
                if (valid) {
                    return chain.filter(exchange);
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            });
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
