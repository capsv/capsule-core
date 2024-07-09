package org.capsule.com.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.capsule.com.utils.htmls.LetterPatternHTML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void send(String to, String subject, String username, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String letter = LetterPatternHTML.generate(username, code);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(letter, true);
            mailSender.send(message);
        } catch (MailException | MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
