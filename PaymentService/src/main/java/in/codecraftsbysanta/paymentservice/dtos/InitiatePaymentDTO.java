package in.codecraftsbysanta.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDTO {

    Long amount;
    String orderId;
    String phoneNumber;
    String name;
    String email;

}
