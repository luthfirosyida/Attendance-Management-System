package com.assessment.Attendance.models.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private long salary;
    private String job;
    // private List<Integer> roles;
}
