// PACKAGE
package com.example.patientManagementApp.controller;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.service.DoctorAppointmentService;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// ANNOTATIONS
@Controller // Marks this class to handle HTTP requests and return views
@RequestMapping("/doctor-appointments") // Common mapping for all doctor-appointment-related endpoints
public class DoctorAppointmentController {

    private final DoctorAppointmentService doctorAppointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Constructor-based dependency injection
    public DoctorAppointmentController(DoctorAppointmentService doctorAppointmentService, DoctorService doctorService, PatientService patientService) {
        this.doctorAppointmentService = doctorAppointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    /**
     * Retrieves all appointments and displays them in the view.
     */
    @Operation(summary = "Get all appointments") // Documentation for a REST API endpoint in Swagger
    @GetMapping // GET /doctor-appointments
    public String getAllAppointments(Model model) {
        List<Appointment> appointments = doctorAppointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "doctor-appointments-list"; // Thymeleaf template for listing appointments
    }

    /**
     * Displays the form to add a new appointment.
     */
    @GetMapping("/add")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "doctor-add-appointment"; // Thymeleaf template name for adding appointment
    }

    /**
     * Saves a new appointment to the system.
     */
    @Operation(summary = "Save new appointment")
    @PostMapping("/save") // POST /doctor-appointments/save
    public String saveAppointment(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            return "doctor-add-appointment"; // Return to form view if validation fails
        }
        doctorAppointmentService.saveAppointment(appointment);
        return "redirect:/doctor-appointments"; // Redirect after saving
    }

    /**
     * Loads the form to update an existing appointment.
     */
    @Operation(summary = "Load update form")
    @GetMapping("/update/{id}") // GET /doctor-appointments/update/{id}
    public String loadUpdateForm(@PathVariable("id") long id, Model model) {
        Optional<Appointment> appointmentOpt = Optional.ofNullable(doctorAppointmentService.getAppointmentById(id));
        if (appointmentOpt.isEmpty()) {
            return "error"; // Handle appointment not found
        }
        Appointment appointment = appointmentOpt.get();
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        return "doctor-update-appointment"; // Thymeleaf template for updating an appointment
    }

    /**
     * Updates the information of an existing appointment.
     */
    @Operation(summary = "Update appointment info")
    @PostMapping("/update/{id}") // POST /doctor-appointments/update/{id}
    public String updateAppointmentInfo(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult result, @PathVariable("id") long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            return "doctor-update-appointment"; // Return to form view if validation fails
        }
        appointment.setId(id); // Ensure the ID is set for the correct record
        doctorAppointmentService.updateAppointment(id, appointment);
        return "redirect:/doctor-appointments"; // Redirect after updating
    }

    /**
     * Deletes an appointment by ID.
     */
    @Operation(summary = "Delete appointment")
    @PostMapping("/delete/{id}") // POST /doctor-appointments/delete/{id}
    public String deleteAppointment(@PathVariable("id") long id) {
        doctorAppointmentService.deleteAppointmentById(id);
        return "redirect:/doctor-appointments"; // Redirect after deletion
    }

    /**
     * Retrieves the details of a specific appointment.
     */
    @Operation(summary = "Get appointment details")
    @GetMapping("/details/{id}")
    public String getAppointmentDetails(@PathVariable("id") Long id, Model model) {
        Optional<Appointment> appointment = Optional.ofNullable(doctorAppointmentService.getAppointmentById(id));
        if (appointment.isPresent()) {
            model.addAttribute("appointment", appointment.get());
            return "doctor-appointment-details";
        } else {
            model.addAttribute("errorMessage", "Appointment not found!");
            return "error"; // Provide an error page or message
        }
    }
}
