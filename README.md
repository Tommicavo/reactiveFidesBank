# FidesBank Project

## Main Technologies
- **Language**: Java 17
- **Maven**: 3.9.6
- **Framework**: Spring Boot 3.2.5
- **Database**: MySQL

## General Information
- **Fully Reactive Application**
- **Microservices Architecture**
- **Dockerized Microservices**

## Reactive Application
#### Reactive applications offer enhanced performance, scalability, and responsiveness by efficiently handling asynchronous I/O operations and providing a smoother user experience under varying workloads.

### Reactive Tools Used
- **Spring WebFlux**: For building reactive web applications.
- **R2DBC**: For reactive database access.
- **WebClient**: For reactive HTTP client operations.

## Microservices
#### Microservices architecture enables scalability, fault isolation, and technology diversity, allowing for independent development, deployment, and scaling of individual services, ultimately fostering agility and resilience in large-scale applications.
### Microservices Tools Used
- **Eureka Server**: For managing service registration and handling service discovery.
- **Eureka Client**: For registering microservices with Eureka Server for discovery.
- **Eureka Gateway**: For routing requests to target microservices after contralized authentication.

## Centralized Authentication with Spring Security and JwtToken
#### Centralized complete Jwt validation is performed in gateway microservice, using Spring Security and JWT Bearer tokens, before routing requests to target microservices.
#### Token presence is also checked in each target microservice for additional security measures and for extracting the logged-in user's role for a role-based security layer in rest controllers.
##