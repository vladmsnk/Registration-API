package com.selfio.selfio.email;

import com.selfio.selfio.entities.User;
import com.selfio.selfio.exceptions.EmailSendingException;
import com.selfio.selfio.repository.UserRepository;
import com.selfio.selfio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, EmailProperties emailProperties) {
        this.javaMailSender = javaMailSender;
        this.emailProperties = emailProperties;
    }

    public void sendEmail(String destination, String text)   {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text, true);
            helper.setSubject("Verify your account!");
            helper.setFrom(emailProperties.getUserName());
            helper.setTo(destination);
            javaMailSender.send(mimeMessage);
            LOGGER.info("message has been sent");
        } catch (Exception e) {
            throw new EmailSendingException(e.getMessage());
        }
    }
}
