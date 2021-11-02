package com.selfio.selfio.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private final JavaMailSenderImpl javaMailSenderImpl;
    private final EmailConfig emailConfig;

    @Autowired
    public EmailSenderService(JavaMailSenderImpl javaMailSenderImpl, EmailConfig emailConfig) {
        this.javaMailSenderImpl = javaMailSenderImpl;
        this.emailConfig = emailConfig;
    }

    @Async
    public void sendEmail(String to, String email) {
        try {
            javaMailSenderImpl.setHost(this.emailConfig.getHost());
            javaMailSenderImpl.setPort(this.emailConfig.getPort());
            javaMailSenderImpl.setUsername(this.emailConfig.getUserName());
            javaMailSenderImpl.setPassword(this.emailConfig.getPassword());

            MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setSubject("Verify your account!");
            helper.setFrom("vyumoiseenkov@gmail.com");
            helper.setTo(to);
            javaMailSenderImpl.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("failed!");
        }
    }
}
