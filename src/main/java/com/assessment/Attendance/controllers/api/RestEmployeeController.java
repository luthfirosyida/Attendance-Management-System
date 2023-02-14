package com.assessment.Attendance.controllers.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.Attendance.models.dto.request.EmployeeDTO;
import com.assessment.Attendance.models.entity.Employee;
import com.assessment.Attendance.services.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class RestEmployeeController {
    
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable int id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee create(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id){
        return employeeService.delete(id);
    }
    
}
