package com.spring.cloud.rest.services.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;


    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        HashMap<String, String> uriVariables = new HashMap<>(); //must put urivariables in hashmap
        uriVariables.put("from", from);
        uriVariables.put("to", to);


        ResponseEntity<CurrencyConversion> entity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}"
        , CurrencyConversion.class, uriVariables); // call currency exchange microservice from here

        CurrencyConversion currencyConversion = entity.getBody();
        if (currencyConversion == null){
            throw new RuntimeException("no currency conversion recieved from exchange with provided values" + from + to + quantity);
        }
        return new CurrencyConversion(currencyConversion.getId(), from, to, 
        currencyConversion.getConversionMultiple(), quantity, 
        quantity.multiply(currencyConversion.getConversionMultiple()), 
        currencyConversion.getEnvironment() + " rest-template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    CurrencyConversion calcCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        HashMap<String, String> uriVariables = new HashMap<>(); //must put urivariables in hashmap
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(), from, to, 
        currencyConversion.getConversionMultiple(), quantity, 
        quantity.multiply(currencyConversion.getConversionMultiple()), 
        currencyConversion.getEnvironment() + " feign");
    }
    
}
