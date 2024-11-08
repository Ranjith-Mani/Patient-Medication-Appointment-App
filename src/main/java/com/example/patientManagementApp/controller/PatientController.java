// PACKAGE
package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Annotations
@Controller // Designates this class as a controller to handle HTTP requests and return views
@RequestMapping("/patients") // Common mapping for all patient-related endpoints
public class PatientController {

    private final PatientService patientService;

    // Constructor-based injection of the PatientService
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET /patients - Retrieve and display a list of all patients
    @GetMapping
    public String getAllPatients(Model model) {
        List<Patient> result = patientService.getAllPatients(); // Get list of all patients
        model.addAttribute("patients", result); // Add the list to the model
        return "patients-list"; // Thymeleaf template for displaying patients
    }

    // GET /patients/add - Display page to add a new patient
    @GetMapping("/add")
    public String loadAddPatientPage(Model model) {
        Patient patient = new Patient(); // Create a new patient object
        model.addAttribute("patient", patient); // Add the patient object to the model
        return "add-patient"; // Thymeleaf template for adding a patient
    }

    // POST /patients/save - Save the newly created patient
    @PostMapping("/save")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient); // Save patient using the service
        return "redirect:/patients"; // Redirect to the patients list after saving
    }

    // GET /patients/update/{id} - Display page to update patient information
    @GetMapping("/update/{id}")
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Patient patient = patientService.getPatientById(id); // Fetch patient by ID
        if (patient == null) {
            return "error"; // Handle the case where the patient is not found
        }
        model.addAttribute("patient", patient); // Add patient object to the model
        return "update-patient"; // Thymeleaf template for updating a patient
    }

    // POST /patients/update/{id} - Update patient information
    @PostMapping("/update/{id}")
    public String updatePatientInfo(@ModelAttribute("patient") Patient patient, @PathVariable("id") long id) {
        patient.setId(id); // Ensure the correct patient is updated
        patientService.updatePatient(patient, id); // Call the update method in the service
        return "redirect:/patients"; // Redirect to the patients list after updating
    }

    // POST /patients/delete/{id} - Delete patient by ID
    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        patientService.deletePatientById(id); // Delete patient using the service
        return "redirect:/patients"; // Redirect to the patients list after deletion
    }

    // GET /patients/details/{id} - View detailed information of a patient
    @GetMapping("/details/{id}")
    public String getPatientDetails(@PathVariable("id") long id, Model model) {
        Patient patient = patientService.getPatientById(id); // Fetch patient details by ID
        if (patient != null) {
            model.addAttribute("patient", patient); // Add the patient to the model
            return "patient-details"; // Thymeleaf template for displaying patient details
        } else {
            return "error"; // Handle the case where the patient is not found
        }
    }
}
