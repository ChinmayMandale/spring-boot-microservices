# spring-boot-microservices
This project is a hands-on architectural overview of spring-boot-microservices

* Phase 1:
  * Created Product Service -> uses MongoDB as Database
  * Created Order Service -> uses MySQL
  * Created Inventory Service -> uses MySQL
 
* Phase 2:
  * Inter service communication between Order Service & Inventory Service
  * Used WebClient for synchronous inter service communication (biz logic demands it to be sync call to check if product is in stock)
  * By default WebClient will make async calls
  * Alternative to WebClient is RestTemplate (sync calls only)

* Phase 3:
  * Introduced discovery server - Spring Netflix Eureka project
  * Helps a micorservice to dynamically find ports of other microservices (Dynamically binds them - no need to soecify port in code)
  * Sends response as service registry - which is cached locally at each microservice
  * If order - inventory comm. established -> discovery server down -> still comm. is facilitated due to cache
  * Newly run services after discovery server is down wont be able to register. Hence wont comm. with others
