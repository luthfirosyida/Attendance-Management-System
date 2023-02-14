package com.assessment.Attendance.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.assessment.Attendance.models.dto.request.LoginRequest;




@Service
public class LoginService {

    private RestTemplate restTemplate;

    @Autowired
    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${server.baseUrl}/login")
    private String url;

    public Boolean login(LoginRequest loginRequest) {
        try {
            ResponseEntity<Map<String,Object>> response
                    = restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            new HttpEntity<>(loginRequest),
                            new ParameterizedTypeReference<Map<String,Object>>() {
                            });
            if (response.getStatusCode() == HttpStatus.OK) {
                setPrinciple(response.getBody(), loginRequest);
                return true;
            }
        } catch (Exception e) {
          throw new UsernameNotFoundException("Username or Password incorrect " + e);
        }
        return false;
    }
    
    public void setPrinciple(Map<String,Object> res, LoginRequest loginRequest){
        // List<GrantedAuthority> authorities = res.get("authorities")
        //         .stream().map(authority -> new SimpleGrantedAuthority(authority.toString()))
        //         .collect(Collectors.toList());

        List<GrantedAuthority> authorities = toListAuth(res.get("authorities"));

               
        UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), 
                        loginRequest.getPassword(), 
                        authorities);
        
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    //mapping optional object to specific list of object
    public List<GrantedAuthority> toListAuth(Object resAuth){
        if (!(resAuth instanceof List<?>)){
            return Collections.emptyList();
        }
        return ((List<?>) resAuth).stream().map(authority -> new SimpleGrantedAuthority(authority.toString()))
                .collect(Collectors.toList());
    }


}
