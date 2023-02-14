package com.assessment.Attendance.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.Attendance.models.dto.request.AttedanceDTO;
import com.assessment.Attendance.services.AttendanceService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/attendance")
@AllArgsConstructor
public class AttendanceController {

    private AttendanceService attendanceService;
   
    @GetMapping
    public String index(Model model) {
        model.addAttribute("attendances", attendanceService.getAll());
        model.addAttribute("isActive", "attendance_history");
        return "attendance/index";
    }
    
    @GetMapping("capture")
    public String capture(Model model) {
        model.addAttribute("attendances", attendanceService.getAll());
        return "webcam/capture-img";
    }

    @GetMapping("/create")
    public String newFile(@ModelAttribute("attendanceDTO") AttedanceDTO attendanceDTO, Model model) {
        model.addAttribute("attendanceDTO", attendanceDTO);
        // model.addAttribute("projects", projectService.getAll());
        model.addAttribute("isActive", "create_request");
  
      return "fileupload/upload_form";
    }
}
