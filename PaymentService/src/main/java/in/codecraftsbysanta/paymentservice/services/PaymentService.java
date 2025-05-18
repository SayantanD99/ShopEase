package in.codecraftsbysanta.paymentservice.services;

import in.codecraftsbysanta.paymentservice.paymentgateways.IPaymentGateway;
import in.codecraftsbysanta.paymentservice.paymentgateways.PaymentGatewayChooserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) {

        IPaymentGateway paymentGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();
        return paymentGateway.createStandardPaymentLink(amount, orderId, phoneNumber, name, email);

    }
}
