package com.citizen.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging Pre Filter. request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                if (config.isPostLogger()) {
                    log.info("Logging Post Filter. response code -> {}", response.getStatusCode());
                }
            }));

            // TODO: 2021-08-21 Filter의 우선순위를 정하는 Ordered를 명시
            // TODO: 2021-08-21 HIGHEST Global 보다 우선순위가 높다.
        }, Ordered.LOWEST_PRECEDENCE);

        return filter;
    }

    @Data
    public static class Config {

        // Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
