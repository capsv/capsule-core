package email.verify.sender.service.services.consumers;

import email.verify.sender.service.config.Constants;
import email.verify.sender.service.models.Verify;
import email.verify.sender.service.services.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyConsumer {

    private final MailSenderService mailSenderService;

    @KafkaListener(topics = Constants.EMAIL_VERIFY_SENDER_TOPIC, groupId = "email-verify-group")
    public void listen(Verify verify) {
        mailSenderService.send(verify.getEmail(), Constants.EMAIL_VERIFICATION_SUBJECT,
            verify.getEmail());
    }
}
