package com.selfio.selfio.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void sendEmail(String destination, String text) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(text, true);
            helper.setSubject("Verify your account!");
            helper.setFrom(emailProperties.getUserName());
            helper.setTo(destination);
            javaMailSender.send(mimeMessage);
            LOGGER.info("email has been sent");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("delivery failed!");
        }
    }
}
