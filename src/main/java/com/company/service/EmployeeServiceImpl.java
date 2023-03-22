package com.company.service;

import com.company.model.Employee;
import com.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.delete(getEmployeeById(id));
    }

    @Override
    public Employee findEmployeeByEmail(String employee) {
        return employeeRepository.findByEmail(employee);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        // Default ASC
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        // First page starts with 0, not 1, so pageNo -1
        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }


}
