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

* Phase 4:
  * API Gateway added to the project
  * All requests will have same port (8080) and will be differentiated based on path (/api/product or /api/order)
  * Only one server port is needed : api-gateway server port (8080) by default
  * Each microservice has a route defined in api-gateway
  * We can add:
    * id,
    * url (url can be load balanced or single servers can be http accessed),
    * predicates (path matcher),
    * filters(apply filter after path is matched to set path)
  * Even discovery server has a route defined
 
* Phase 5:
  * Added Keycloak OAuth2 security authentication
  * Configured docker instance of keycloak
  * included issuer url in app.prop file of api-gateway
  * configured bean of securitywebfilterchain on api-gateway to enable authentication (as api-gateway is based on spring-webflux project & not spring mvc)
  * configured bean of security filterchain on discovery-server to enable authentication (this one is based on spring-mvc)
 
* Phase 6:
  * Added Resilience 4J (based on Netflix Hystrix library) for circuit breaking
  * Conceptual learning
    > Circuit breaking :
    > Why?
    >> API calls can be made from one service to other. There are chances that the sender sends calls but receiver is down
    >> In such cases, sender might keep sending call & receiver wont receive any & its waste of resources
    >
    > Then?
    >>  Stop sending calls after 5 simultaneously failed calls
    >
    > How to implement:
    >> Add props in app.prop file
    >> Add annotations to the call inside controller of sender, where we comm between 2 microservices
    >> Add fallback methods
    >
    > Additional features:
    >> Can configure timeout & retries
    >> If receiver does not respond in time, connection is timed out - exception thrown
    >> If connection does not respond in configured retry time, retry sending request again
  
  * Changes in order-service application.properties file
  * Order Controller & particularly the api call where we communicate with other microservice

* Phase 7:
  * Added Distributed Tracing. Installed zipkin using docker. Running locally on port 9411 (default)
  * This helps in tracing each API call from start to end. Helps lookup any errors. Debugging is easy
  * Changes to app.prop files of each microservice to enable tracing
  * We can customize probability of api calls to be traced. (1.0 means 100% of calls sent through tracing)
  * If we want to customize a span id/name of any call:
    * Add extra observatory capabilities inside business logic (service layer)
    * Added it in order service just before it makes call to inventory service
    * Added observationregistry
    * Created a lookup object of Observation
    * set cardinality value
    * The logic to call inventory service is put inside this object.observe method
    * similar to Angular Observables
  * Conceptual learning:
    > What?
    >> Distributed Tracing is a design pattern which allows observability to each API call. Adds granularity & helps maintainability.
    >
    > How?
    >> Install Zipkin
    >> Each API call that user makes goes through multiple microservices (assume), then the call gets assigned a span id (unique) in each microservice.
    >> We can customize span IDs as well
    >> If API call fails / is taking long time -> lookup in zipkin
    >
    > Additional features:
    >>  Can configure probability of API calls traced through Zipkin
 



