package com.selfio.selfio.email;

import com.selfio.selfio.exceptions.EmailSendingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * The Service provides email sending.
 */
@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public EmailSender(JavaMailSender javaMailSender, EmailProperties emailProperties) {
        this.javaMailSender = javaMailSender;
        this.emailProperties = emailProperties;
    }

    /**
     * The method provides sending confirmation email to user.
     * @param destination is a user's email.
     * @param text is a internal content of the mail.
     * @throws EmailSendingException if email could not be sent.
     */
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
