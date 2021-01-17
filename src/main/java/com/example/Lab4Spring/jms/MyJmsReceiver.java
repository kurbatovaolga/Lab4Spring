package com.example.Lab4Spring.jms;


import com.example.Lab4Spring.entity.Email;
import com.example.Lab4Spring.entity.Event;
import com.example.Lab4Spring.repos.EmailRepo;
import com.example.Lab4Spring.repos.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyJmsReceiver {

    private final EmailSenderService emailSenderService;
    private final EventRepo eventRepo;
    private final EmailRepo emailRepo;

    @Autowired
    public MyJmsReceiver(EmailSenderService emailSenderService, EventRepo eventRepo, EmailRepo emailRepo) {
        this.emailSenderService = emailSenderService;
        this.eventRepo = eventRepo;
        this.emailRepo = emailRepo;
    }

    @JmsListener(destination = "eventbox", containerFactory = "myFactory")
    public void receiveEvent(Event event) {
        eventRepo.save(event);
    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        emailSenderService.send(email);
        emailRepo.save(email);
    }
}