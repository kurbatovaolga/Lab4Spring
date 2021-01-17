package com.example.Lab4Spring.jms;

import com.example.Lab4Spring.entity.Email;
import com.example.Lab4Spring.entity.Employee;
import com.example.Lab4Spring.entity.Event;
import com.example.Lab4Spring.entity.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSender {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsSender(JmsTemplate jmsTemplate){
        this.jmsTemplate= jmsTemplate;
    }
    public void sendEmployeeUpdate (Employee employee, EventType eventType){
        Email email = new Email();
        email.setReceiver("usertest2121esa@gmail.com");
        email.setSubject("Внесены [" + eventType.name() + ']');
        String body = String.format(
                employee.getText());
        email.setBody(body);
        jmsTemplate.convertAndSend("mailbox", email);
    }
    public <T> void sendEvent(Class<T> entityClass, T entity, EventType eventType){
        Event event = new Event();
        event.setEventType(eventType);
        event.setEntity(entity.toString());
        event.setEntityClass(entityClass.getSimpleName());
        jmsTemplate.convertAndSend("eventbox", event);
    }
}

