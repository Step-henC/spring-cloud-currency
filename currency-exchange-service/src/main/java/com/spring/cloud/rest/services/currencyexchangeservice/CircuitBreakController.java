package com.spring.cloud.rest.services.currencyexchangeservice;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class CircuitBreakController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakController.class);

    @GetMapping("/sample-api") 
    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse") //the name provided after the in the application properties for resilience4j.retry.instances...
    public String sampleApi(){ //make retry annotation name = "default" for default behavior

        logger.info("Sample API call received"); //default retries is 3
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("localhost:8080/some-dummy", String.class); //bs call we know will fail to test circuitbreaker
        return forEntity.getBody(); 
    }

    public String hardCodedResponse(Exception ex){ //need a throwable parameter for fallbackmethod
                                                    //exception class extends throwable so we can add it here
        return "Fallback-response";
    }

    @GetMapping("/another-sample-api") 
    @CircuitBreaker(name = "default", fallbackMethod = "anotherHardCodedResponse")
    public String anotherSampleApi(){ //to activate circuit breaker either refresh browser alot or..
                                        //cli "watch -n 0.1 curl http://localhost:8000/sample-api" for 10 reqs/second
                                        //you will see circuitbreaker responds without calling method

        logger.info("Another Sample API call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("localhost:8080/some-dummy", String.class);
        return forEntity.getBody();
    }

    @GetMapping("/sample-api-rate") 
    @RateLimiter(name = "default")
    @Bulkhead(name = "default") //how many concurrent calls
    public String rateLimitSampleApi(){ 

        logger.info("Another Sample API call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("localhost:8080/some-dummy", String.class);
        return forEntity.getBody();
    }
    

    public String anotherHardCodedResponse(Exception ex){ //need a throwable parameter for fallbackmethod
                                                    //exception class extends throwable so we can add it here
        return "Another Fallback-response";
    }



}

