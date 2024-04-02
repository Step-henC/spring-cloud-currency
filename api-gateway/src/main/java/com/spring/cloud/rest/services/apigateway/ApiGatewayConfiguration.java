package com.spring.cloud.rest.services.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){        
        return builder.routes().route(p -> p.path("/currency-exchange/**") //regex path match
            .uri("lb://currency-exchange-service")) //load balance with eureka server logical name
            //resulting url: http://localhost:8765/currency-exchange/from/USD/to/INR
           .route(p -> p.path("/currency-conversion/**") //regex path match
            .uri("lb://currency-conversion-service"))
            .route(p -> p.path("/currency-conversion-feign/**") //regex path match
            .uri("lb://currency-conversion-service"))
            .route(p -> p.path("/currency-conversion-madeup/**")
            .filters(f -> f.rewritePath("currency-conversion-madeup/(?<segment>.*)", "currency-conversion-feign/${segment}")) //regex path match
            .uri("lb://currency-conversion-service"))
            .build(); 
    }

}
