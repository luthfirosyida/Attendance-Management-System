package com.assessment.Attendance.models.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private long salary;
    private String job;
    private Boolean isActive;
    private int counter;
    private Employee manager;
    // private List<Role> roles;
    
}
