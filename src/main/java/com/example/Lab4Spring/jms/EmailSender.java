package com.example.Lab4Spring.jms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private String sendEmail = "usertest2121esa@gmail.com";
    private final JavaMailSender mailSender;

    @Autowired
    public EmailSender(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }


    public void send(String eventType, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Менеджер");
        message.setTo(sendEmail);
        message.setSubject(eventType);
        message.setText(body);
        mailSender.send(message);
    }


}
