# User Auth Service

## Overview
The **User Auth Service** is a Spring Boot-based application designed to handle user authentication and authorization. It provides features such as user registration, login, token generation, and validation.

## Features
- User registration with role assignment.
- Secure password storage using BCrypt.
- JWT-based authentication and token validation.
- Role-based access control.
- Integration with Kafka for event-driven communication.

## Technologies Used
- **Java**: Core programming language.
- **Spring Boot**: Framework for building the application.
- **Maven**: Dependency management and build tool.
- **SQL**: Database for storing user and session data.
- **JWT**: Token-based authentication.
- **Kafka**: Event-driven messaging.
- **BCrypt**: Password hashing.

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- PostgreSQL or any SQL-compatible database
- Kafka (for event-driven communication)

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/SayantanD99/user-auth-service.git
   cd user-auth-service
   ```
2. Configure the database: Update the application.xml file with your database credentials.
3. Build the project:
```mvn clean install
```
4. Run the application:
```
mvn spring-boot:run
```
5. Access the API at http://localhost:8080.

## API Endpoints
### Authentication
- POST /auth/signup: Register a new user. 
- POST /auth/login: Login and receive a JWT token.
### User Management
- GET /users/{id}: Retrieve user details by ID.
### Token Validation
- POST /auth/validate: Validate a JWT token.