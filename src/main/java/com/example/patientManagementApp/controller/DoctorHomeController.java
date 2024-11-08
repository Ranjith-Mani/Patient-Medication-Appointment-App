package com.example.patientManagementApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorHomeController {

    /**
     * Displays the home page for doctors.
     */
    @GetMapping("/doctors-home")
    public String DoctorHomePage() {
        return "Doctors-home"; // Thymeleaf template for the doctors' home page
    }
}
