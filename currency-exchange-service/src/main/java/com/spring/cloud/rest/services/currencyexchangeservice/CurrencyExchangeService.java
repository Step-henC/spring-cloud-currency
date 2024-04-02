package com.spring.cloud.rest.services.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeService {
    

    @Autowired
    private CurrencyExchangeRepository repository;


    CurrencyExchange currencyExchangeByFromAndTo(String from, String to){

        CurrencyExchange exchange = repository.findByFromAndTo(from, to);
        if (exchange == null){
            throw new RuntimeException("Cannot find Currency Exchange " + from + "to " + to);
        }
        return exchange;
    }
}
