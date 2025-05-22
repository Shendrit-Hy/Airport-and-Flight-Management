package com.mbi_re.airport_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending support-related email notifications.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    /**
     * Sends a support notification email.
     *
     * @param to      the recipient email address
     * @param subject the subject of the email
     * @param message the body of the email
     */
    public void sendSupportNotification(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(env.getProperty("spring.mail.username")); // sender email
        mailSender.send(email);
    }
}
