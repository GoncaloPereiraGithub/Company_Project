package com.company.service;

import com.company.model.Employee;
import java.util.List;

// Default interface for services classes
public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
}
