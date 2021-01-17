package com.example.Lab4Spring.repos;

import com.example.Lab4Spring.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {
}

