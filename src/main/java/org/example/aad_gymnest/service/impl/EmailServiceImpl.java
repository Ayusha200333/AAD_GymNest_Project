package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

@Autowired
private JavaMailSender mailSender;

    @Async
    @Override
    public void sendGuideRegistrationEmail(String toEmail, String guideName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Guide Registration Successful");
        message.setText("Dear " + guideName + ",\n\nYour registration as a guide is successful!\n\nBest Regards,\nGymNest Team");
        try {
            System.out.println("Attempting to send email to: " + toEmail + " at " + new java.util.Date());
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
            throw e;
        }
    }

}
