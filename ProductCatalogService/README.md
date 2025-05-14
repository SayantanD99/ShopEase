# Product Catalog Service

This repository contains the source code for the Product Catalog Service, a Spring Boot application that manages products and categories. The service provides RESTful APIs to create, retrieve, and update products.  

# Technologies Used
- Java
- Spring Boot
- Maven
- SQL

# Getting Started

## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- A SQL database (e.g., MySQL, PostgreSQL)

## Installation

- Clone the repository:
  
  ``` 
  git clone https://github.com/SayantanD99/product-catalog-service.git

  cd product-catalog-service
  ```
  
- Configure the database connection in application.properties:

  ```  
  spring.datasource.url=jdbc:mysql://localhost:3306/productdb
  
  spring.datasource.username=root
  
  spring.datasource.password=yourpassword
  
  spring.jpa.hibernate.ddl-auto=update
  ```
  
- Build the project:
   
  ```
  mvn clean install
  ```
  
- Run the application:
   
  ```
  mvn spring-boot:run
  ```
  
# API Endpoints

## Get All Products

- URL: /products
- Method: GET
- Response:
  
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

## Get Product by ID
- URL: /products/{productID}
- Method: GET
- Response:

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

## Create Product
- URL: /products
- Method: POST
- Request Body:

```
{
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

- Response:

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

## Replace Product
- URL: /products/{productID}
- Method: PUT
- Request Body:

```
{
    "name": "Iphone 14 Pro",
    "description": "Latest iPhone Pro model",
    "price": 1099.99,
    "imgUrl": "http://example.com/iphone14pro.jpg",
    "category": {
        "id": 1,
        "name": "Electronics",
        "description": "Electronic items"
    }
}
```

- Response:
```
{
    "id": 1,
    "name": "Iphone 14 Pro",
    "description": "Latest iPhone Pro model",
    "price": 1099.99,
    "imgUrl": "http://example.com/iphone14pro.jpg",
    "category": {
        "id": 1,
        "name": "Electronics",
        "description": "Electronic items"
    }
}
```
