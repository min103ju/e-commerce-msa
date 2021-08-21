package com.citizen.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

// TODO: 2021-08-21 application.yml에 있는 gateway route 관련 속성을 java code로 설정 가능
//@Configuration
public class FilterConfig {

    // TODO: 2021-08-21 Spring Security와 같이 따로 Filter에 대한 chain 같은 처리를 안해줘도 자연스럽게 되네?
    // TODO: 2021-08-21 어떤 원리인지 파악
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r ->
                // TODO: 2021-08-21 yml의 spring.cloud.gateway.routes.predicates
                r.path("/first-service/**")
                    // TODO: 2021-08-21 Http Header에 request와 response를 명시해줄 수 있다.
                    .filters(f ->
                        f.addRequestHeader("first-request", "first-request-header")
                            .addResponseHeader("first-response", "first-response-header")
                    )
                    // TODO: 2021-08-21 yml의 spring.cloud.gateway.routes.uri
                    .uri("http://localhost:8081")
            )
            .route(r ->
                r.path("/second-service/**")
                    .filters(f ->
                        f.addRequestHeader("second-request", "second-request-header")
                            .addResponseHeader("second-response", "second-response-header")
                    )
                    .uri("http://localhost:8082")
            )
            .build();
    }
}
