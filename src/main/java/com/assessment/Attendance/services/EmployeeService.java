/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assessment.Attendance.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assessment.Attendance.models.dto.request.EmployeeDTO;
import com.assessment.Attendance.models.entity.Employee;



/**
 *
 * @author MSI-JO
 */
@Service
public class EmployeeService {

    private RestTemplate restTemplate;

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/employee")
    private String url;


    /**
     * @return
     */
    public List<Employee> getAll() {
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Employee>>() {
                }).getBody();
    }


    public Employee getById(int id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee create(EmployeeDTO employeeDTO) {
        System.out.println("employeeservice");
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(employeeDTO),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
                
    }

    public Employee update(int id, EmployeeDTO employeeDTO) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.PUT, new HttpEntity<>(employeeDTO),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee delete(int id) {
        return restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();  
    }

    
}
