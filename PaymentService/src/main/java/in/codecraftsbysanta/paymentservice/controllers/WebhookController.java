package in.codecraftsbysanta.paymentservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripeWebhook")
public class WebhookController {

    @PostMapping
    public void respondToWebhook(@RequestBody String event) {

        System.out.println(event);

    }
}


/*
// Server.java
//
// Use this sample code to handle webhook events in your integration.
//
// Using Maven:
// 1) Paste this code into a new file (src/main/java/com/stripe/sample/Server.java)
//
// 2) Create a pom.xml file. You can quickly copy one from an official Stripe Sample.
//   curl https://raw.githubusercontent.com/stripe-samples/accept-a-payment/8e94e50e68072c344f12b02f65a6240e1c656d4a/custom-payment-flow/server/java/pom.xml -o pom.xml
//
// 3) Build and run the server on http://localhost:4242
//   mvn compile
//   mvn exec:java -Dexec.mainClass=com.stripe.sample.Server

package com.stripe.sample;

import static spark.Spark.post;
import static spark.Spark.port;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
        import com.stripe.net.Webhook;

public class Server {
    public static void main(String[] args) {
        // The library needs to be configured with your account's secret key.
        // Ensure the key is kept out of any version control system you might be using.
        Stripe.apiKey = "sk_test_...";

        // This is your Stripe CLI webhook secret for testing your endpoint locally.
        String endpointSecret = "whsec_4f48bbcf344869013115a871a63d2b4396296fcadc4e4afca538f86d2f34955b";

        port(4242);

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            Event event = null;

            try {
                event = Webhook.constructEvent(
                        payload, sigHeader, endpointSecret
                );
            } catch (JsonSyntaxException e) {
                // Invalid payload
                response.status(400);
                return "";
            } catch (SignatureVerificationException e) {
                // Invalid signature
                response.status(400);
                return "";
            }

            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
            // Handle the event
            System.out.println("Unhandled event type: " + event.getType());

            response.status(200);
            return "";
        });
    }
}
*/

