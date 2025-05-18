package in.codecraftsbysanta.paymentservice.paymentgateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements IPaymentGateway {

    @Value("${stripe.apikey}")
    private String stripeApiKey;

    @Value("${stripe.priority:1}") // Default priority if not set in properties
    private int priority;

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public String createStandardPaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) {

        try {

            Stripe.apiKey = this.stripeApiKey;

            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(getPrice(amount).getId())
                                            .setQuantity(1L)
                                            .build()
                            ).setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder().
                                    setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT).
                                    setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder().
                                            setUrl("https://scaler.com").build())
                                    .build())
                            .build();
            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();

        }
        catch (StripeException exception) {

            throw new RuntimeException(exception.getMessage());

        }

    }

    private Price getPrice(Long amount) {

        try {

            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amount)
                            .setRecurring( //Remove in case of one time payment
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()
                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                            )
                            .build();
            Price price = Price.create(params);
            return price;

        } catch (StripeException exception) {

            throw new RuntimeException(exception.getMessage());

        }
    }
}