package in.codecraftsbysanta.paymentservice.paymentgateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest; // Kept as per existing, consider removing if not strictly needed for this unit test

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest // Consider replacing with @ExtendWith(MockitoExtension.class) for a more focused unit test
class PaymentGatewayChooserStrategyTest {

    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;
    private IPaymentGateway gateway1;
    private IPaymentGateway gateway2;

    @BeforeEach
    void setUp() {
        // Mocks are re-initialized for each test, specific arrangements will be in test methods
        gateway1 = mock(IPaymentGateway.class);
        gateway2 = mock(IPaymentGateway.class);
    }

    @Test
    void testGetBestPaymentGateway_SelectsLowestPriority() {
        // Arrange
        when(gateway1.getPriority()).thenReturn(2);
        when(gateway2.getPriority()).thenReturn(1);
        paymentGatewayChooserStrategy = new PaymentGatewayChooserStrategy(List.of(gateway1, gateway2));

        // Act
        IPaymentGateway bestGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();

        // Assert
        assertEquals(gateway2, bestGateway, "Should select the gateway with the lowest priority value.");
    }

    @Test
    void testGetBestPaymentGateway_SingleGateway() {
        // Arrange
        when(gateway1.getPriority()).thenReturn(1);
        paymentGatewayChooserStrategy = new PaymentGatewayChooserStrategy(List.of(gateway1));

        // Act
        IPaymentGateway bestGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();

        // Assert
        assertEquals(gateway1, bestGateway, "Should select the only available gateway.");
    }

    @Test
    void testGetBestPaymentGateway_EmptyGatewayList() {
        // Arrange
        paymentGatewayChooserStrategy = new PaymentGatewayChooserStrategy(Collections.emptyList());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            paymentGatewayChooserStrategy.getBestPaymentGateway();
        });
        assertEquals("No payment gateways configured.", exception.getMessage(), "Exception message should indicate no gateways are configured.");
    }

    @Test
    void testGetBestPaymentGateway_NullGatewayList() {
        // Arrange
        paymentGatewayChooserStrategy = new PaymentGatewayChooserStrategy(null);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            paymentGatewayChooserStrategy.getBestPaymentGateway();
        });
        assertEquals("No payment gateways configured.", exception.getMessage(), "Exception message should indicate no gateways are configured for null list.");
    }

    @Test
    void testGetBestPaymentGateway_GatewaysWithSamePriority() {
        // Arrange
        when(gateway1.getPriority()).thenReturn(1);
        when(gateway2.getPriority()).thenReturn(1); // Same priority as gateway1
        // The stream().min() behavior with equal elements is to return the first encountered.
        // So, gateway1 is expected if List.of(gateway1, gateway2) is used.
        paymentGatewayChooserStrategy = new PaymentGatewayChooserStrategy(List.of(gateway1, gateway2));


        // Act
        IPaymentGateway bestGateway = paymentGatewayChooserStrategy.getBestPaymentGateway();

        // Assert
        // Depending on the stability of the sort/min operation, this could be gateway1 or gateway2.
        // For Comparator.comparingInt, min is stable, so it should return the first element
        // from the original list that has the minimum value.
        assertEquals(gateway1, bestGateway, "Should select one of the gateways if priorities are the same (typically the first one encountered).");
    }
}