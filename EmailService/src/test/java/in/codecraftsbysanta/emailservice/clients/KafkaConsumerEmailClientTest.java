package in.codecraftsbysanta.emailservice.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.codecraftsbysanta.emailservice.dtos.EmailDTO;
import in.codecraftsbysanta.emailservice.utils.EmailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Session;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerEmailClientTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaConsumerEmailClient kafkaConsumerEmailClient;

    private EmailDTO emailDTO;
    private String jsonMessage;

    @BeforeEach
    void setUp() {
        emailDTO = new EmailDTO();
        emailDTO.setTo("test@example.com");
        emailDTO.setFrom("sender@example.com");
        emailDTO.setSubject("Test Subject");
        emailDTO.setBody("Test Body");

        jsonMessage = "{\"to\":\"test@example.com\",\"from\":\"sender@example.com\",\"subject\":\"Test Subject\",\"body\":\"Test Body\"}";
    }

    @Test
    void testSendEmail_Success() throws JsonProcessingException {
        when(objectMapper.readValue(jsonMessage, EmailDTO.class)).thenReturn(emailDTO);

        try (MockedStatic<EmailUtil> mockedEmailUtil = Mockito.mockStatic(EmailUtil.class)) {
            kafkaConsumerEmailClient.sendEmail(jsonMessage);

            mockedEmailUtil.verify(() -> EmailUtil.sendEmail(
                    any(Session.class),
                    eq(emailDTO.getTo()),
                    eq(emailDTO.getSubject()),
                    eq(emailDTO.getBody())
            ), times(1));
            verify(objectMapper, times(1)).readValue(jsonMessage, EmailDTO.class);
        }
    }

    @Test
    void testSendEmail_JsonProcessingException() throws JsonProcessingException {
        when(objectMapper.readValue(anyString(), eq(EmailDTO.class)))
                .thenThrow(new JsonProcessingException("Error processing JSON") {});

        assertThrows(RuntimeException.class, () -> {
            kafkaConsumerEmailClient.sendEmail("invalid json message");
        });

        verify(objectMapper, times(1)).readValue("invalid json message", EmailDTO.class);
        // EmailUtil.sendEmail should not be called in this case
        try (MockedStatic<EmailUtil> mockedEmailUtil = Mockito.mockStatic(EmailUtil.class)) {
            mockedEmailUtil.verifyNoInteractions();
        }
    }
}