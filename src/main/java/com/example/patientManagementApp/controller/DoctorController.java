package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller to handle doctor-related HTTP requests
@Controller
@RequestMapping("/doctors") // Common mapping for all doctor-related endpoints
public class DoctorController {

    private final DoctorService doctorService;

    // Constructor-based dependency injection for DoctorService
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Retrieves and lists all doctors.
     */
    @GetMapping
    public String getAllDoctors(Model model) {
        List<Doctor> result = doctorService.getAllDoctors();
        model.addAttribute("doctors", result);
        return "doctors-list"; // Thymeleaf template for listing doctors
    }

    /**
     * Loads the form to add a new doctor.
     */
    @GetMapping("/add")
    public String loadAddDoctorPage(Model model) {
        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        return "add-doctor"; // Thymeleaf template for adding a doctor
    }

    /**
     * Saves a new doctor to the system.
     */
    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doc) {
        doctorService.saveDoctor(doc);
        return "redirect:/doctors"; // Redirect after saving
    }

    /**
     * Loads the update form for an existing doctor.
     */
    @GetMapping("/update/{id}")
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id); // Retrieve doctor using service
        if (doctor == null) {
            return "error"; // Handle doctor not found
        }
        model.addAttribute("doctor", doctor);
        return "update-doctor"; // Thymeleaf template for updating a doctor
    }

    /**
     * Updates the information of an existing doctor.
     */
    @PostMapping("/update/{id}")
    public String updateDoctorInfo(@ModelAttribute("doctor") Doctor doc, @PathVariable("id") long id) {
        doc.setId(id); // Ensure the correct ID is set to update the doctor
        doctorService.updateDoctor(id, doc);
        return "redirect:/doctors"; // Redirect after updating
    }

    /**
     * Deletes a doctor by ID.
     */
    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") long id) {
        doctorService.deleteDoctorById(id);
        return "redirect:/doctors"; // Redirect after deletion
    }

    /**
     * Retrieves the details of a specific doctor.
     */
    @GetMapping("/details/{id}")
    public String getDoctorDetails(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            return "doctor-details"; // Thymeleaf template for doctor details
        } else {
            return "error"; // Handle doctor not found
        }
    }
}
