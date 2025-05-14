# 🛒 ShopEase – Microservices-Based E-Commerce Platform

## Overview
**ShopEase** is a scalable, modular e-commerce platform built using a microservices architecture. It demonstrates modern software engineering practices including MVC design, RESTful APIs, relational database modeling, and third-party payment integration with Stripe & Razorpay. Core services include **User Authentication, Product Catalog, Payment Service, and Service Discovery**.

## 🔧 Key Features
- **User Management**: Registration, login, and role-based access control.
- **Product Catalog**: Category-wise inventory management with product image support.
- **Order & Payment Processing**: Secure order placement via **Stripe & Razorpay** checkout sessions with success/failure handling.
- **Service-Oriented Architecture**: Independently deployable microservices with service discovery for dynamic routing.
- **Database Design**: MySQL-based normalized schema using foreign keys and indexing for performance.
- **Error Handling**: Custom exceptions (ResourceNotFoundException, etc.) for user-friendly error reporting.

## 💻 Tech Stack
- **Backend**: Java, Spring Boot, Spring Web, Spring Data, Spring Cloud
- **Database**: MySQL, Hibernate ORM
- **Payment Gateway**: Stripe, RazorPay
- **Caching**: Redis
- **Tools**: Lombok, JPA, Maven, JUnit, Mockito
- **Service Discovery**: Netflix Eureka

## 🚀 Project Highlights
- **Stripe & Razorpay Integration**: Secure and production-grade payment handling.
- **Performance Optimizations**: Indexed queries and planned Redis caching to reduce latency.
- **Scalable Design**: Microservices allow independent scaling and easier maintenance.
- **Queuing Service**: Asynchronous messaging with **Kafka** for order and email processing.
- **Extensibility**: Well-suited for frontend integration (e.g., React, Angular).
- **Automation & CI/CD Ready**: Maven-based project structure, suitable for Jenkins or GitHub Actions.

## ⚠️ Known Limitations
- Stripe / Razorpay transaction costs may affect small-scale apps.
- Caching needs proper invalidation strategies.
- Monitoring and observability tools like ELK Stack or Sentry are not yet integrated.

## 🔄 Future Enhancements
- Frontend implementation using React or Angular.
- Implementation of Spring AI.
- Integration of APM tools like New Relic for observability.

## 📁 Project Structure (MVC Pattern)

```markdown
project-root/
├── controller/
├── service/
├── model/
├── repository/
├── exception/
├── resources/
│   ├── application.properties
│   └── schema.sql
├── test/
└── pom.xml
```

## 🧪 Getting Started

- Prerequisites: Java 17+, Spring, MySQL, Maven, Stripe API keys, Razorpay API keys
- Run Instructions:
`
git clone https://github.com/yourusername/onlineshop.git
cd onlineshop
mvn clean install
mvn spring-boot:run
`
- Access: http://localhost:8080

## 📘 API Endpoints Sample
### Get All Products

- **URL**: /products
- **Method**: GET
- **Response**:
  
```
    {
        "id": 1,
        "name": "Iphone 14",
        "description": "Latest iPhone model",
        "price": 999.99,
        "imgUrl": "http://example.com/iphone14.jpg",
        "category": {
            "id": 1,
            "name": "Electronics",
            "description": "Electronic items"
        }
    }
```

## 🤝 Contributing & License
Open to contributions! Licensed under MIT.
