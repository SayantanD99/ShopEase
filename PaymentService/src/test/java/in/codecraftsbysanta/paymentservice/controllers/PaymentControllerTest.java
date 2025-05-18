package in.codecraftsbysanta.paymentservice.controllers;

import in.codecraftsbysanta.paymentservice.dtos.InitiatePaymentDTO;
import in.codecraftsbysanta.paymentservice.services.IPaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPaymentService paymentService;

    @Test
    void testInitiatePayment() throws Exception {
        // Arrange
        InitiatePaymentDTO initiatePaymentDto = new InitiatePaymentDTO();
        initiatePaymentDto.setAmount(1000L);
        initiatePaymentDto.setOrderId("order_123");
        initiatePaymentDto.setPhoneNumber("1234567890");
        initiatePaymentDto.setName("Test User");
        initiatePaymentDto.setEmail("test@example.com");

        String expectedPaymentLink = "http://example.com/payment_link";

        when(paymentService.getPaymentLink(
                initiatePaymentDto.getAmount(),
                initiatePaymentDto.getOrderId(),
                initiatePaymentDto.getPhoneNumber(),
                initiatePaymentDto.getName(),
                initiatePaymentDto.getEmail()
        )).thenReturn(expectedPaymentLink);

        // Act & Assert
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1000,\"orderId\":\"order_123\",\"phoneNumber\":\"1234567890\",\"name\":\"Test User\",\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedPaymentLink));

        Mockito.verify(paymentService).getPaymentLink(
                initiatePaymentDto.getAmount(),
                initiatePaymentDto.getOrderId(),
                initiatePaymentDto.getPhoneNumber(),
                initiatePaymentDto.getName(),
                initiatePaymentDto.getEmail()
        );
    }
}