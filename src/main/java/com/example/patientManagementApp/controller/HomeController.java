package com.example.patientManagementApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Handle GET request for /home
    @GetMapping("/home")
    public String doctorHomePage() {
        return "Home"; // Return the Home view
    }
}
