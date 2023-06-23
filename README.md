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
