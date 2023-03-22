package com.company.controller;

import com.company.dto.UserRegistrationDto;
import com.company.model.Employee;
import com.company.model.User;
import com.company.repository.EmployeeRepository;
import com.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired // Automatic Dependency Injection
    private EmployeeService employeeService;

    // Display list of employees
    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "firstName", "asc", model);
    }

    // Display New Employee Form
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // Create model attribute to bind form data
        Employee employee = new Employee();
        // Thymeleaf will access empty employee object to bind form data
        model.addAttribute("employee", employee);
        return "new_employee"; // Go to new_employee.html
    }

    // Display Form To Update
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        // Get employee from the service
        Employee employee = employeeService.getEmployeeById(id);
        // Set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    // Take Employee from FORM(UI)
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {

        // If employee email is already registered in DB, return error
        Employee employeeEntryCheck = employeeRepo.findByEmail(employee.getEmail());
        if (employeeEntryCheck != null){
            return "redirect:/showNewEmployeeForm?error";
        }

        // Saving employee
        employeeService.saveEmployee(employee);
        return "redirect:/showNewEmployeeForm?success";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {

        // Temporary save of employee id
        long employeeId = employeeService.getEmployeeById(employee.getId()).getId();
        // If employee email is already registered in DB, return error
        Employee employeeEntryCheck = employeeRepo.findByEmail(employee.getEmail());
        if (employeeEntryCheck != null){
            return "redirect:/showFormForUpdate/" + employeeId + "?error";
        }

        // Saving employee
        employeeService.saveEmployee(employee);
        return "redirect:/showFormForUpdate/" + employeeId + "?success";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        // Call delete method in service
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5; // Choosing pageSize/Configure in UI a @PathVariable

        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
        // List of Employees paginated
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo); // Current page
        model.addAttribute("totalPages", page.getTotalPages()); // Num of total pages
        model.addAttribute("totalItems", page.getTotalElements()); // Num of rows
        model.addAttribute("sortField", sortField); // Sorting field
        model.addAttribute("sortDir", sortDir); // Sorting Direction
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc"); // Clicking on fields toggle
        model.addAttribute("listEmployees", listEmployees);
        return "index";
    }
}
