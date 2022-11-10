package com.company.repository;

import com.company.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Exposes Databases operations for Employee Entity with ID (<Employee, Long(ID)>)
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
