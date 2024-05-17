package api.gateway.dev.filters;

import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class LogFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        String body = exchange.getRequest().getBody().toString();
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();

        log.info("***************API-GATEWAY**************");
        log.info("Path of the Request Received: {}", path);
        log.info("Request Body: {}", body);
        if (remoteAddress != null) {
            log.info("IP Address of Requestor: {}", remoteAddress.getAddress());
            log.info("Port of Requestor: {}", remoteAddress.getPort());
        } else {
            log.info("IP Address of Requestor: unknown");
            log.info("Port of Requestor: unknown");
        }

        return chain.filter(exchange);
    }
}