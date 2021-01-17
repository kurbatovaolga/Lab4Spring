package com.example.Lab4Spring.repos;

import com.example.Lab4Spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, String > {
}

