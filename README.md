# What is This?

Currency conversion using Spring Cloud for microservices. Uses in memory H2 database with data.sql script populating db on startup
The currency-excahnge-service pulls from db to map to currency-conversion-service. Currency-conversion-service makes use of Feign client 
to communicate with Eureka discovery service and allow Eureka to load balance. The api-gate is a spring cloud gateway 
that can route all requests to each of the  currency-conversion-service and  currency-exchange-service with Eureka/netflix
still load balancing.

# How To run
If maven install and Java 17, you can run in code editor or in each project root cli argument `mvn spring-boot:run`
The Eureka service (naming-server) will show all active servers on localhost:8671. For multiple  currency-exchange-service instances, I open another VS Code window
with the currency-exchange-service and add in launch settings.json the following configuration
		- `"vmArgs": "-Dserver.port=8001"` and run again	

As a result of multiple instances of currency-exchange-service, we can make use of the feign proxy in the currency-conversion-service to see the load-balancing of Eureka toggling ports 8000 and 8001. 

## Can you explain the load balancing thing?
The currency-conversion-service pulls data from the currency-exchange-service (see diagram below). The currency-conversion-service has two ways it *can* do this:
	1. Using RestTemplate to make a standard api call.
		- this method has rest template postpended to the environment property of the response
	2. Feign proxy by spring
		- This method uses the `currency-conversion-feign/*` endpoint and Feign serves as proxy that communicates with Eureka
			and allows Eureka to load balance the services for us
So when we add the `"vmArgs": "-Dserver.port=8001"` we have a service at ports 8000 and 8001 that Eureka should load balance.. if we use the feign endpoint.

# Eureka Example Page of services running
![Screenshot (49)](https://github.com/Step-henC/spring-cloud-currency/assets/98792412/4a6d07fb-e3a0-48e0-bd71-7401dd18372e)

# Diagram of Data flow
![spring-cloud-diagram](https://github.com/Step-henC/spring-cloud-currency/assets/98792412/324088f3-6485-4327-9dcf-5a2f5059a96e)



