package com.wabs.website.services;

import com.wabs.website.models.Email;
import com.wabs.website.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Async
    public void sendEmail(String subject, String body, boolean toSubscribers) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(body);

        List<Email> emails = toSubscribers ? emailRepository.findAllWithUpdates() : emailRepository.findAll();

        for (Email email : emails) {
            message.setTo(email.getEmail());
            mailSender.send(message);
        }
    }

}
