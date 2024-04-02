package com.spring.cloud.rest.services.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name ="currency-exchange-service", url="localhost:8000") 
//instead of hardcoding url, we can have feign talk to eureka to pick up
// all instances of currency-exhange and eureka load balances between the instances
@FeignClient(name ="currency-exchange-service") 
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}") //copied from exchange service
    public CurrencyConversion retrieveExchangeValue(@PathVariable String from, 
    @PathVariable String to);
}
