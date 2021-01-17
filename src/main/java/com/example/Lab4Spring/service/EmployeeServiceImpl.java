package com.example.Lab4Spring.service;

import com.example.Lab4Spring.entity.Employee;
import com.example.Lab4Spring.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void create(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public List<Employee> readAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee read(int id) {
        return employeeRepo.getOne(id);
    }

    @Override
    public boolean update(Employee employee, int id) {
        if (employeeRepo.existsById(id)) {
            employee.setId(id);
            employeeRepo.save(employee);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
