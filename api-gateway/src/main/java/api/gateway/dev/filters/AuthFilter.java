package api.gateway.dev.filters;

import api.gateway.dev.dtos.requests.TokenReqst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient webClient;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token == null || token.isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return isJwtValid(token).flatMap(isValid -> {
                if (Boolean.TRUE.equals(isValid)) {
                    return chain.filter(exchange);
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            });
        };
    }

    private Mono<Boolean> isJwtValid(String token) {
        String jwt = token.substring(7);
        return webClient.post().uri("http://localhost:8080/auth/api/v1.0/validate") //lb://auth-service
            .bodyValue(new TokenReqst(jwt)).retrieve().bodyToMono(Boolean.class)
            .onErrorResume(e -> Mono.just(false));
    }

    public static class Config {

    }
}
