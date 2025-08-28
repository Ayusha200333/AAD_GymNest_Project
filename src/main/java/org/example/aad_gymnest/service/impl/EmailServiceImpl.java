package org.example.aad_gymnest.service.impl;

import org.example.aad_gymnest.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendGuideRegistrationEmail(String toEmail, String guideName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Guide Registration Successful");
        message.setText("Dear " + guideName + ",\n\nYour registration as a guide is successful!\n\nBest Regards,\nGymNest Team");
        mailSender.send(message);
    }
}
