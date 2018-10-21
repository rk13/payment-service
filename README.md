# Payments

## Banking api as you wished it would be

Payment service can receive and perform money transfers between accounts

Payments service holds information about customer accounts and accepts money transfer requests from other services.
Payment orders should come from our customer accounts only (debtor).
Otherwise, service reports that it is not allowed to make money transfer.

If payment beneficiary (creditor) account belongs to another our customer payment service executes order immediately 
and creates double transaction entries transfer operation respectively related to proper accounts and payment orders.
Otherwise, service keeps payment order in pending state.

Payment service implements minimalistic field validation constraints and rules
(f.e. not valid to have creditor and debtor accounts).

## Modules

* server - REST API for money transfers
* func-test - Cucumber BDD tests 

## Technologies

* Jersey / Dropwizard
* Swagger / OpenAPI
* Hibernate / H2
* Google Guice
* Lombok

* Cucumber
* RestAssured
* JUnit
* Mockito

## Commands

Running server with gradle
```
./gradlew server:run
```

Running server with `start-server.sh`
```
./gradlew build
```
```
./start-server.sh
```

Running all cucumber scenarios (server should be running)
```
./gradlew func-tests:cucumber
```

## Useful links and tips

Server OpenAPI specification generated from annotations

[http://localhost:8080/openapi.json](http://localhost:8080/openapi.json)

Server Swagger UI

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

![Server Swagger UI](swagger-ui.png?raw=true)

Compiler annotation processors should be enabled in IDEA

![Server Swagger UI](idea.png?raw=true)

