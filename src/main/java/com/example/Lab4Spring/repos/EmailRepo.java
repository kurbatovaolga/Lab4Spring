package com.example.Lab4Spring.repos;

import org.springframework.data.repository.CrudRepository;
import com.example.Lab4Spring.entity.Email;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepo extends  CrudRepository <Email, Long> {
}
