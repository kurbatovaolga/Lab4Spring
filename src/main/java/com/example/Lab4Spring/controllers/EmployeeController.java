package com.example.Lab4Spring.controllers;

import com.example.Lab4Spring.entity.*;
import com.example.Lab4Spring.jms.JmsSender;
import com.example.Lab4Spring.repos.EmployeeRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final JmsSender jmsSender;
    private final EmployeeRepo repo;

    @Autowired
    public EmployeeController(JmsSender jmsSender, EmployeeRepo repo) {
        this.jmsSender = jmsSender;
        this.repo = repo;
    }

    @GetMapping
    public List<Employee> list() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable("id")Employee employee) {
        return employee;
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        employee.setCreationDate(LocalDateTime.now());
        return repo.save(employee);
    }

    @PutMapping("{id}")
    public Employee update(
            @PathVariable ("id") Employee employeefromDb,
            @RequestBody Employee employee) {
        BeanUtils.copyProperties(employee,employeefromDb,"id");

        return repo.save(employeefromDb);

    }

    @DeleteMapping("{id}")

    public void delete (@PathVariable ("id") Employee employee) {
        repo.delete(employee);
    }
}