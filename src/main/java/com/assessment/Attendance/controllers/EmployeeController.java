package com.assessment.Attendance.controllers;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.Attendance.services.EmployeeService;

import lombok.AllArgsConstructor;




@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;
    // private ProjectService projectService;
    // private OvertimeService overtimeService;

    @GetMapping
    public String index(Model model) {
        // model.addAttribute("overtimes", overtimeService.getAll());
        // model.addAttribute("projects", projectService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        return "employee/index";
    }
}

