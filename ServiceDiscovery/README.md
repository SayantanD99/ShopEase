# Service Discovery

This project is a **Service Discovery** application built using **Spring Boot** and **Netflix Eureka Server**. It acts as a registry for microservices, enabling them to discover and communicate with each other.

## Features

- Implements Netflix Eureka Server for service discovery.
- Configurable via `application.properties`.
- Runs on port `8761` by default.

## Prerequisites

- Java 17 or higher
- Maven 3.8 or higher

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/SayantanD99/ServiceDiscovery.git
cd ServiceDiscovery
```

### Build the Project

```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

### Access the Eureka Dashboard
The Eureka Server will be available at http://localhost:8761.

### Configuration
The application is configured using the application.properties file:
```
spring.application.name=ServiceDiscovery
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```
- server.port: Specifies the port on which the application runs.
- eureka.client.register-with-eureka: Disables self-registration of the Eureka server.
- eureka.client.fetch-registry: Disables fetching of the registry.