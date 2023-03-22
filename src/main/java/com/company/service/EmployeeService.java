package com.company.service;

import com.company.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

// Default interface for services classes
public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    // Added to check if is there an employee already registered with an email
    Employee findEmployeeByEmail(String employee);
}
