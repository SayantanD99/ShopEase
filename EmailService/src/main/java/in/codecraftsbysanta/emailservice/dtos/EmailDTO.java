package in.codecraftsbysanta.emailservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailDTO {
    String to;
    String from;
    String subject;
    String body;
}