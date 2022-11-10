package com.company.service;

import com.company.model.Employee;
import com.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired // Automatic Dependency Injection
    private EmployeeRepository employeeRepository;

    @Override // findAll returns a list of all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override // Saving an employee
    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long id) {
        // Returns an object
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException("Employee not found for id : " + id);
        }
        return employee;
    }
}
