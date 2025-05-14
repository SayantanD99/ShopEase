package in.codecraftsbysanta.emailservice.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import in.codecraftsbysanta.emailservice.dtos.EmailDTO;
import in.codecraftsbysanta.emailservice.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class KafkaConsumerEmailClient {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = {"signup","order_created"},groupId = "emailService")
    public void sendEmail(String message) {
        try {
            EmailDTO emailDTO = objectMapper.readValue(message, EmailDTO.class);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailDTO.getFrom(), "ptykogfbncyuyggj");
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDTO.getTo(),emailDTO.getSubject(), emailDTO.getBody());


        }catch (JsonProcessingException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }


}