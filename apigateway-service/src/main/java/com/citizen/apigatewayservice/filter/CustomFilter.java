package com.citizen.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// TODO: 2021-08-21 CustomFilter는 Service(Routing)마다 filter를 적용하는 것.
// TODO: 2021-08-21 전체 적용되는 GlobalFilter와 차이점이 있다.
@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre filter

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom Pre filter request id -> {}", request.getId());

            // Custom Post filter
            // TODO: 2021-08-21 Mono. Webflux(spring 5에서 추가)에서 비동기 방식의 단일값을 반환할 때 사용
            // TODO: 2021-08-21 Webflux에선 ServletHttpRequest, Response를 더이상 사용하지 않고,
            // TODO: 2021-08-21 ServerHttpRequest, Response를 사용하는데 이를 도와주는게, ServerWebExchange(exchange)이다.
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom Post filter response id -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}
