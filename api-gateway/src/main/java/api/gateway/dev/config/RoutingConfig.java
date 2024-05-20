package api.gateway.dev.config;

import api.gateway.dev.filters.AuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoutingConfig {

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
            .route("auth-service", r -> r.path("/auth/**")
                .uri("lb://auth-service"))
            .route("account-management-service", r -> r.path("/profile/**")
                .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
                .uri("lb://account-management-service"))
            .build();
    }
}