package in.codecraftsbysanta.emailservice.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailUtilTest {

    private final String toEmail = "recipient@example.com";
    private final String subject = "Test Subject";
    private final String body = "Test Body Content";
    private final String fromEmailAddress = "no_reply@example.com";
    private final String fromEmailPersonal = "NoReply-JD";

    @Test
    void testSendEmail_Success() throws Exception {
        try (MockedStatic<Transport> mockedTransport = Mockito.mockStatic(Transport.class)) {
            // Create a real session
            Session session = Session.getInstance(new Properties());

            // Call the method
            EmailUtil.sendEmail(session, toEmail, subject, body);

            // Verify Transport.send() was called
            mockedTransport.verify(() -> Transport.send(any(MimeMessage.class)), times(1));
        }
    }

    @Test
    void testSendEmail_MessagingException() throws Exception {
        try (MockedStatic<Transport> mockedTransport = Mockito.mockStatic(Transport.class)) {
            // Mock Transport.send() to throw an exception
            mockedTransport.when(() -> Transport.send(any(MimeMessage.class)))
                    .thenThrow(new MessagingException("Failed to send"));

            // Create a real session
            Session session = Session.getInstance(new Properties());

            // Call the method
            EmailUtil.sendEmail(session, toEmail, subject, body);

            // Verify Transport.send() was called
            mockedTransport.verify(() -> Transport.send(any(MimeMessage.class)), times(1));
        }
    }

    @Test
    void testSendEmail_VerifyMessageContent() throws Exception {
        try (MockedStatic<Transport> mockedTransport = Mockito.mockStatic(Transport.class)) {
            // Create a real session
            Session session = Session.getInstance(new Properties());

            // Capture the MimeMessage
            ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);

            // Call the method
            EmailUtil.sendEmail(session, toEmail, subject, body);

            // Verify Transport.send() and capture the message
            mockedTransport.verify(() -> Transport.send(messageCaptor.capture()));
            MimeMessage capturedMessage = messageCaptor.getValue();

            // Assert message content
            assertEquals(subject, capturedMessage.getSubject());
            assertEquals(body, capturedMessage.getContent().toString().trim());
            assertEquals(1, capturedMessage.getRecipients(Message.RecipientType.TO).length);
            assertEquals(toEmail, capturedMessage.getRecipients(Message.RecipientType.TO)[0].toString());
            assertEquals(fromEmailPersonal + " <" + fromEmailAddress + ">", capturedMessage.getFrom()[0].toString());
            assertTrue(new Date().getTime() - capturedMessage.getSentDate().getTime() < 5000);
        }
    }
}