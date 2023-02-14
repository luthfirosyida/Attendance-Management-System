package com.assessment.Attendance.models.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    private int id;
    private LocalDateTime date;
    private String description;
    private String workplace;
    private int body_temperature;
    private String selfie_photo;
    private Employee employee;
    
}
