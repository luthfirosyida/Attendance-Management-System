package com.assessment.Attendance.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttedanceDTO {
    
    private Integer employeeId;
    private String date;
    private String description;
    private String workplace;
    private int body_temperature;
    private String selfie_photo;
    
}
