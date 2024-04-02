package com.spring.cloud.rest.services.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    //just creating this interface is sufficient to talk to database
    

    CurrencyExchange findByFromAndTo(String from, String to); //spring jpa will auto create sql 
}
