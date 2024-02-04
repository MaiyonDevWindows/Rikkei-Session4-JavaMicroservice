package com.maiyon.service;

import com.maiyon.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @KafkaListener(id = "emailService", topics = "apache-kafka-demo")
    @Async
    public void sendMail(UserDTO user){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("maiyonaisu1102@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText("Register successfully.");
        simpleMailMessage.setSubject("Register successfully.");
        javaMailSender.send(simpleMailMessage);
    }
}
