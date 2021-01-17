package com.example.Lab4Spring.controllers;

import com.example.Lab4Spring.entity.BasicEntity;
import com.example.Lab4Spring.entity.Email;
import com.example.Lab4Spring.entity.Employee;
import com.example.Lab4Spring.jms.JmsSender;
import com.example.Lab4Spring.repos.EmployeeRepo;
import com.example.Lab4Spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final JmsSender jmsSender;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(JmsSender jmsSender,
                              EmployeeService employeeService) {
        this.jmsSender = jmsSender;
        this.employeeService = employeeService;
    }

    @Autowired
    EmployeeRepo employeeRepo;

    @GetMapping
    public ResponseEntity<List<Employee>> read() {
        final List<Employee> employees = employeeService.readAll();

        return employees != null &&  !employees.isEmpty()
                ? new ResponseEntity<>(employees, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> read(@PathVariable(name = "id") int id) {
        final Employee employee = employeeService.read(id);

        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        employee.setCreationDate(LocalDateTime.now());
        Employee newEmployee = new Employee();
        jmsSender.sendObjectUpdate((BasicEntity) newEmployee, "add");
        String body = "Сотрудник " + newEmployee + " был добавлен";
        Email email = new Email(UUID.randomUUID().toString(), body);
        jmsSender.sendEmail(email);
        employeeService.create(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
        final boolean updated = employeeService.update(employee, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @DeleteMapping("{id}")

    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        Employee employee  = this.employeeRepo.findById(id).orElse(null);
        jmsSender.sendObjectUpdate((BasicEntity) employee, "delete");
        String body = "Сотрудник " + employee + " был уволен";
        Email email = new Email(UUID.randomUUID().toString(), body);
        jmsSender.sendEmail(email);
        final boolean deleted = employeeService.delete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}