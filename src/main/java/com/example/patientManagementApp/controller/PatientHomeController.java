// PACKAGE
package com.example.patientManagementApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientHomeController {

    // Mapping for the patients home page
    @GetMapping("/patients-home")
    public String showPatientsHomePage() {
        return "Patients-home"; // Returns the Patients-home view
    }
}
