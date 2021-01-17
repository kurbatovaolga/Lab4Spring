package com.example.Lab4Spring.jms;


import com.example.Lab4Spring.entity.Email;
import com.example.Lab4Spring.entity.Event;
import com.example.Lab4Spring.repos.EmailRepo;
import com.example.Lab4Spring.repos.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {

    private final EmailSender emailSender;
    private final EventRepo eventRepo ;
    private final EmailRepo emailRepo ;

    @Autowired
    public JmsReceiver(EmailSender emailSender, EventRepo eventRepo, EmailRepo emailRepo) {
        this.emailSender = emailSender;
        this.eventRepo = eventRepo;
        this.emailRepo = emailRepo;
    }

    @JmsListener(destination = "events", containerFactory = "factory")
    public void receiveChange(Event event) {
        eventRepo.save(event);
    }

    @JmsListener(destination = "emails", containerFactory = "factory")
    public void receiveEmail(Email email) {
        email.setEmail("usertest2121esa@gmail.com");
        emailRepo.save(email);
        emailSender.send(email.getBody(), email.toString());
    }
}
