package com.spring.cloud.rest.services.currencyexchangeservice;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class CurrencyExchange {

    @Id
    private Long id;

    @Column(name="currency_from")
    private String from; //from is an SQL keyword

    @Column(name="currency_to")
    private String to;

    private BigDecimal conversionMultiple;

    private String environment; //describes instance for load balancing since multi instances

    public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
        this.id = id; //did not use lombok constructor because do not want all 
        this.from = from; //args (environment) in contructor
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }

    

    public CurrencyExchange() { //NEED a default constructor!!!!!!!!!!!!
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    
    
}
