package api.gateway.dev.filters;

import api.gateway.dev.dtos.responses.Credentials;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || token.isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();

            }
            return isJwtValid(token)
                .flatMap(credentials -> {
                    if (credentials != null && credentials.getUsername() != null) {
                        log.info("Authenticating success for {}", credentials.getUsername());
                        return chain.filter(exchange);
                    } else {
                        log.info("Authentication failed");
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                }).onErrorResume(e -> {
                    log.error("Error during JWT validation: ", e);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }).subscribeOn(Schedulers.boundedElastic());
        };
    }

    private Mono<Credentials> isJwtValid(String token) {
        String jwt = token.substring(7);
        return webClientBuilder.build().get().uri("lb://auth-service/api/v1/auth/validate")
            .header("Authorization", jwt).retrieve().bodyToMono(Credentials.class);
    }

    public static class Config {

    }
}
