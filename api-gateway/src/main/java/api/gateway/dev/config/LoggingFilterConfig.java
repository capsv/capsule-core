package api.gateway.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class LoggingFilterConfig {

    @Bean
    public GlobalFilter loggingFilter() {
        return (exchange, chain) -> {
            logRequest(exchange);
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> logResponse(exchange)));
        };
    }

    private void logRequest(ServerWebExchange exchange) {
        var request = exchange.getRequest();
        log.info("Request: " + request.getMethod() + " " + request.getURI());
    }

    private void logResponse(ServerWebExchange exchange) {
        var response = exchange.getResponse();
        log.info("Response status code: " + response.getStatusCode());
    }
}
