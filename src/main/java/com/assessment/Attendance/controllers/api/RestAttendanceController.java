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

import com.assessment.Attendance.models.dto.request.AttedanceDTO;
import com.assessment.Attendance.models.entity.Attendance;
import com.assessment.Attendance.services.AttendanceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@AllArgsConstructor
public class RestAttendanceController {


    private AttendanceService attendanceService;

    @GetMapping
    public List<Attendance> getAll() {
        return attendanceService.getAll();
    }

    @GetMapping("/{id}")
    public Attendance getById(@PathVariable int id) {
        return attendanceService.getById(id);
    }

    @PostMapping
    public Attendance create(@RequestBody AttedanceDTO attendanceDTO) {
        return attendanceService.create(attendanceDTO);
    }

    @PutMapping("/{id}")
    public Attendance update(@PathVariable int id, @RequestBody AttedanceDTO attendanceDTO) {
        return attendanceService.update(id, attendanceDTO);
    }

    @DeleteMapping("/{id}")
    public Attendance delete(@PathVariable int id){
        return attendanceService.delete(id);
    }
}
