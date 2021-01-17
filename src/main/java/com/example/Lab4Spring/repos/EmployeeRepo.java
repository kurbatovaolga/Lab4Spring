package com.example.Lab4Spring.repos;


import com.example.Lab4Spring.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo  extends JpaRepository<Employee, Integer> {

}