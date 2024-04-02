# What is This?

Currency conversion using Spring Cloud for microservices. Uses in memory H2 database with data.sql script populating db on startup
The currency-excahnge-service pulls from db to map to currency-conversion-service. Currency-conversion-service makes use of Feign client 
to communicate with Eureka discovery service and allow Eureka to load balance. The api-gate is a spring cloud gateway 
that can route all requests to each of the  currency-conversion-service and  currency-exchange-service with Eureka/netflix
still load balancing.

# How To run
If maven install and Java 17, you can run in code editor or in each project root cli argument mvn run spring:boot
The Eureka service (naming-server) will show all active servers on localhost:8671. For multiple  currency-exchange-service instances, I open another VS Code window
with the currency-exchange-service and add in launch settings.json the following configuration
		"vmArgs": "-Dserver.port=8001" and run again	
