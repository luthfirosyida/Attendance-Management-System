package com.assessment.Attendance.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.assessment.Attendance.models.dto.request.LoginRequest;
import com.assessment.Attendance.services.LoginService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthController {

    private LoginService loginService;

    @GetMapping("/login")
    public String loginView(LoginRequest loginRequest, Authentication auth) {
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "auth/login-app";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) {
        if (!loginService.login(loginRequest)) {
            return "redirect:/login?error=true";
        }
        return "redirect:/dashboard";
    }

  
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("isActive", "dashboard");
        System.out.println(authentication.getAuthorities());
        return "dashboard";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    return "redirect:/login?logout";

    }

}
    

