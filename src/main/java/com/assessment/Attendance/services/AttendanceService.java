package com.assessment.Attendance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assessment.Attendance.models.dto.request.AttedanceDTO;
import com.assessment.Attendance.models.entity.Attendance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Service
public class AttendanceService {

    private RestTemplate restTemplate;

    @Autowired
    public AttendanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/attendance")
    private String url;

    public List<Attendance> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Attendance>>() {
                }).getBody();
    }

    public Attendance getById(int id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Attendance>() {
                }).getBody();
    }

    public Attendance create(AttedanceDTO attendanceDTO) {
        System.out.println("employeeservice");
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(attendanceDTO),
                new ParameterizedTypeReference<Attendance>() {
                }).getBody();
                
    }

    public Attendance update(int id, AttedanceDTO attendanceDTO) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity<>(attendanceDTO),
                new ParameterizedTypeReference<Attendance>() {
                }).getBody();
    }

    public Attendance delete(int id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Attendance>() {
                }).getBody();  
    }



    
}
