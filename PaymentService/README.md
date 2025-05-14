# Payment Service

The Payment Service is a Spring Boot-based application that provides APIs for initiating payments and handling webhooks for payment gateways like Razorpay and Stripe. It is designed to simplify the integration of payment processing into your application.

## Technologies Used

- **Java**: Programming language used for development.
- **Spring Boot**: Framework for building the application.
- **Maven**: Build and dependency management tool.
- **Razorpay SDK**: For integrating Razorpay payment gateway.
- **Stripe SDK**: For handling Stripe webhooks.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java 17 or higher
- Maven 3.8 or higher
- An IDE like IntelliJ IDEA (optional)
- Razorpay and Stripe accounts for API keys

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/payment-service.git
   cd payment-service
   ```
   
2. Configure the application properties:
    - Open the src/main/resources/application.properties file.
    - Add your Razorpay and Stripe credentials:
   
         ```
         razorpay.id=your_razorpay_key
         razorpay.secret=your_razorpay_secret
         ```

3. Build the project:
      ```
      mvn clean install
      ```

4. Run the application:
    ```
    mvn spring-boot:run
    ```

5. The application will start on http://localhost:8080.


## API Endpoints
### Payment API
- Endpoint: /payment
- Method: POST
- Description: Initiates a payment and returns a payment link.
- Request Body:
```
{
"amount": 1000,
"orderId": "order_123",
"phoneNumber": "9876543210",
"name": "John Doe",
"email": "john.doe@example.com"
}
```

- Response:
```
{
"paymentLink": "https://razorpay.com/payment-link"
}
```

### Webhook API
- Endpoint: /stripeWebhook
- Method: POST
- Description: Handles Stripe webhook events.
- Request Body: Raw JSON payload sent by Stripe.
- Response: Logs the event details to the console.