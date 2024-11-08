// PACKAGE
package com.example.patientManagementApp.controller;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientAppointmentService;
import com.example.patientManagementApp.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ANNOTATIONS
@Controller // Marks this class to handle HTTP requests and return views
@RequestMapping("/patient-appointments") // Common mapping for all patient-appointment-related endpoints
public class PatientAppointmentController {

    private final PatientAppointmentService patientAppointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Constructor-based dependency injection
    public PatientAppointmentController(PatientAppointmentService patientAppointmentService,
                                        DoctorService doctorService,
                                        PatientService patientService) {
        this.patientAppointmentService = patientAppointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // Get all appointments
    @Operation(summary = "Get all appointments") // Documentation for a REST API endpoint in Swagger
    @GetMapping // GET /patient-appointments
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = patientAppointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "patient-appointments-list"; // Thymeleaf template for listing appointments
    }

    // Show add appointment form
    @GetMapping("/add")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient-add-appointment"; // Thymeleaf template name for adding appointment
    }

    // Save new appointment
    @Operation(summary = "Save new appointment")
    @PostMapping("/save") // POST /patient-appointments/save
    public String saveAppointment(@ModelAttribute("appointment") @Valid Appointment appointment,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            return "patient-add-appointment"; // Return to form view if validation fails
        }
        patientAppointmentService.saveAppointment(appointment);
        return "redirect:/patient-appointments"; // Redirect after saving
    }

    // Load update form
    @Operation(summary = "Load update form")
    @GetMapping("/update/{id}") // GET /patient-appointments/update/{id}
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Appointment appointment = patientAppointmentService.getAppointmentById(id);
        if (appointment == null) {
            return "error"; // Handle appointment not found
        }
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient-update-appointment"; // Thymeleaf template for updating an appointment
    }

    // Update appointment info
    @Operation(summary = "Update appointment info")
    @PostMapping("/update/{id}") // POST /patient-appointments/update/{id}
    public String updateAppointmentInfo(@ModelAttribute("appointment") @Valid Appointment appointment,
                                        BindingResult result,
                                        @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            return "patient-update-appointment"; // Return to form view if validation fails
        }
        appointment.setId(id); // Ensure the ID is set for the correct record
        patientAppointmentService.updateAppointment(id, appointment);
        return "redirect:/patient-appointments"; // Redirect after updating
    }

    // Delete appointment
    @Operation(summary = "Delete appointment")
    @PostMapping("/delete/{id}") // POST /patient-appointments/delete/{id}
    public String deleteAppointment(@PathVariable("id") long id) {
        patientAppointmentService.deleteAppointmentById(id);
        return "redirect:/patient-appointments"; // Redirect after deletion
    }

    // Get appointment details
    @Operation(summary = "Get appointment details")
    @GetMapping("/details/{id}") // GET /patient-appointments/details/{id}
    public String getAppointmentDetails(@PathVariable Long id, Model model) {
        Appointment appointment = patientAppointmentService.getAppointmentById(id);
        if (appointment != null) {
            model.addAttribute("appointment", appointment);
            return "patient-appointment-details"; // Thymeleaf template for appointment details
        } else {
            return "error"; // Handle appointment not found
        }
    }
}
