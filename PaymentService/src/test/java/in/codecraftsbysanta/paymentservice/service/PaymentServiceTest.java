package in.codecraftsbysanta.paymentservice.services;

import in.codecraftsbysanta.paymentservice.paymentgateways.IPaymentGateway;
import in.codecraftsbysanta.paymentservice.paymentgateways.PaymentGatewayChooserStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Mock
    private IPaymentGateway paymentGateway;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testGetPaymentLink() {
        // Arrange
        Long amount = 1000L;
        String orderId = "order_123";
        String phoneNumber = "1234567890";
        String name = "Test User";
        String email = "test@example.com";
        String expectedPaymentLink = "http://example.com/payment_link";

        when(paymentGatewayChooserStrategy.getBestPaymentGateway()).thenReturn(paymentGateway);
        when(paymentGateway.createStandardPaymentLink(amount, orderId, phoneNumber, name, email))
                .thenReturn(expectedPaymentLink);

        // Act
        String actualPaymentLink = paymentService.getPaymentLink(amount, orderId, phoneNumber, name, email);

        // Assert
        assertEquals(expectedPaymentLink, actualPaymentLink);
        verify(paymentGatewayChooserStrategy).getBestPaymentGateway();
        verify(paymentGateway).createStandardPaymentLink(amount, orderId, phoneNumber, name, email);
    }
}