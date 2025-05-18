package in.codecraftsbysanta.paymentservice.paymentgateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class PaymentGatewayChooserStrategy {

    private final List<IPaymentGateway> paymentGateways;

    @Autowired
    public PaymentGatewayChooserStrategy(List<IPaymentGateway> paymentGateways) {
        this.paymentGateways = paymentGateways;
    }

    public IPaymentGateway getBestPaymentGateway() {
        if (paymentGateways == null || paymentGateways.isEmpty()) {
            throw new IllegalStateException("No payment gateways configured.");
        }

        // Sort by priority (lower number means higher priority)
        // and return the first one.
        // Or, if you prefer, find the minimum.
        return paymentGateways.stream()
                .min(Comparator.comparingInt(IPaymentGateway::getPriority))
                .orElseThrow(() -> new IllegalStateException("Could not determine the best payment gateway."));
    }
}