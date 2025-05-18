# Email Service

## Overview
The Email Service is a Spring Boot application designed to send emails based on messages received from Kafka topics. It supports sending emails for events such as user signups and order creation. The service uses JavaMail for email handling and integrates with Kafka for message consumption.

---

## Features
- Consumes messages from Kafka topics (`signup`, `order_created`).
- Sends emails using SMTP with configurable properties.
- Supports HTML email content.
- Includes unit tests for utility and client classes.

---

## Technologies Used
- **Java**: Core programming language.
- **Spring Boot**: Framework for building the application.
- **Kafka**: Message broker for consuming events.
- **JavaMail**: Library for email handling.
- **Maven**: Dependency management and build tool.
- **Mockito**: For mocking and unit testing.

---

### Kafka Topics
The service listens to the following Kafka topics:
- **`signup`**: Triggered when a user signs up.
- **`order_created`**: Triggered when an order is created.

### Kafka Message Format
The Kafka message should be a JSON object with the following structure:
```json
{
  "to": "recipient@example.com",
  "from": "sender@example.com",
  "subject": "Email Subject",
  "body": "Email Body Content"
}
```

---

## Email Sending Process
1. The service consumes a message from a Kafka topic. 
2. The message (JSON string) is deserialized into an EmailDTO object. 
3. SMTP properties are configured, and a javax.mail.Session is created. 
4. The EmailUtil.sendEmail method is called with the session and EmailDTO details to construct and send a MimeMessage.

---

## Example Responses
### Success Response
When an email is successfully sent, the application logs will show:
```
Message is ready
Email Sent Successfully!!
```
### Error Response
If there is an error during message processing or email sending, a RuntimeException might be thrown and logged, for example:
```
RuntimeException: Error processing JSON
```

Or, if email sending fails:
```
javax.mail.MessagingException: Failed to send email
```
---

## Running the Application
1. Clone the repository:
```
git clone https://github.com/SayantanD99/email-service.git
```
2. Navigate to the project directory:
```
cd email-service
```
3. Build the project using Maven:
```
mvn clean install
```
4. Run the application:
```
mvn spring-boot:run
```
Ensure your Kafka and SMTP server details are correctly configured.

---

## Testing
Unit tests are provided for key components of the service. To run the tests:
```
mvn test
```
The tests cover:
- **EmailUtil**: Verifies email construction and sending logic (mocking Transport.send).
- **KafkaConsumerEmailClient**: Verifies Kafka message processing and interaction with EmailUtil.