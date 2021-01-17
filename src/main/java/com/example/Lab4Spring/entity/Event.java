package com.example.Lab4Spring.entity;


import javax.persistence.*;

@Entity
@Table(schema = "public", name = "event")
public class Event {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "entity_class")
    private String entityClass;

    @Column(name = "entity_field")
    private String entityField;

    public Event(){}

    public Event(String id, String eventType, String entityClass, String entityField) {
        this.id = id;
        this.eventType = eventType;
        this.entityClass = entityClass;
        this.entityField = entityField;
    }
}