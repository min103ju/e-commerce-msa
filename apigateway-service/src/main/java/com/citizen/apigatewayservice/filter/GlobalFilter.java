package com.citizen.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// TODO: 2021-08-21 CustomFilter는 Service(Routing)마다 filter를 적용하는 것.
// TODO: 2021-08-21 전체 적용되는 GlobalFilter와 차이점이 있다. 
// TODO: 2021-08-21 GlobalFilter의 경우 CustomFilter보다 앞선에서 시작하고 뒤에 종료된다.
@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Global Pre filter

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Global Filter baseMessage : {}", config.getBaseMessage());

            // TODO: 2021-08-21 application.yml에서 관리
            if (config.isPreLogger()) {
                log.info("Global Filter Start. request id -> {}", request.getId());
            }

            // Global Post filter
            // TODO: 2021-08-21 Mono. Webflux(spring 5에서 추가)에서 비동기 방식의 단일값을 반환할 때 사용
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                if (config.isPostLogger()) {
                    log.info("Global Filter End. response code -> {}", response.getStatusCode());
                }
            }));
        };
    }

    // TODO: 2021-08-21 Setter가 없을 경우 yml에 있는 properties를 주입받지 못한다. 
    @Data
    public static class Config {

        // Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
