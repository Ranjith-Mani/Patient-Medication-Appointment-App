package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.service.DoctorMedicationService;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor-medications")
public class DoctorMedicationController {

    private final DoctorMedicationService doctorMedicationService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Constructor injection for services
    public DoctorMedicationController(DoctorMedicationService doctorMedicationService,
                                      DoctorService doctorService,
                                      PatientService patientService) {
        this.doctorMedicationService = doctorMedicationService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // GET /doctor-medications - List all medications
    @GetMapping
    public String getAllMedications(Model model) {
        List<Medication> medications = doctorMedicationService.getAllMedications();
        model.addAttribute("medications", medications);
        return "doctor-medication-list"; // Thymeleaf template for listing medications
    }

    // GET /doctor-medications/patient/{patientId} - List medications for a specific patient
    @GetMapping("/patient/{patientId}")
    public String getMedicationsByPatient(@PathVariable("patientId") long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            model.addAttribute("error", "Patient not found.");
            return "error"; // Custom error template
        }
        List<Medication> medications = doctorMedicationService.getMedicationsByPatient(patient);
        model.addAttribute("medications", medications);
        return "doctor-medication-list";
    }

    // GET /doctor-medications/doctor/{doctorId} - List medications for a specific doctor
    @GetMapping("/doctor/{doctorId}")
    public String getMedicationsByDoctor(@PathVariable("doctorId") long doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            model.addAttribute("error", "Doctor not found.");
            return "error"; // Custom error template
        }
        List<Medication> medications = doctorMedicationService.getMedicationsByDoctor(doctor);
        model.addAttribute("medications", medications);
        return "doctor-medication-list";
    }

    // GET /doctor-medications/details/{id} - View details of a specific medication
    @GetMapping("/details/{id}")
    public String getMedicationDetails(@PathVariable("id") long id, Model model) {
        Medication medication = doctorMedicationService.getMedicationById(id);
        if (medication != null) {
            model.addAttribute("medication", medication);
            return "doctor-medication-details"; // Thymeleaf template for medication details
        } else {
            model.addAttribute("error", "Medication not found.");
            return "error";
        }
    }

    // GET /doctor-medications/add - Load add medication form
    @GetMapping("/add")
    public String addMedicationForm(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Patient> patients = patientService.getAllPatients();

        model.addAttribute("medication", new Medication());
        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);

        // Check if there are no doctors or patients
        if (doctors.isEmpty() || patients.isEmpty()) {
            model.addAttribute("error", "No doctors or patients available to assign medication.");
            return "error";
        }

        return "doctor-medication-form"; // Thymeleaf template for adding medication
    }

    // POST /doctor-medications/add - Save a new medication
    @PostMapping("/add")
    public String addMedication(@ModelAttribute Medication medication, Model model) {
        if (medication.getDoctor() == null || medication.getPatient() == null) {
            model.addAttribute("error", "Doctor and Patient must be selected.");
            return "doctor-medication-form"; // Re-show the form with an error message
        }

        doctorMedicationService.saveMedication(medication);
        return "redirect:/doctor-medications"; // Redirect after saving
    }

    // GET /doctor-medications/edit/{id} - Load edit medication form
    @GetMapping("/edit/{id}")
    public String editMedicationForm(@PathVariable("id") long id, Model model) {
        Medication medication = doctorMedicationService.getMedicationById(id);
        if (medication != null) {
            List<Doctor> doctors = doctorService.getAllDoctors();
            List<Patient> patients = patientService.getAllPatients();

            model.addAttribute("medication", medication);
            model.addAttribute("doctors", doctors);
            model.addAttribute("patients", patients);
            return "doctor-medication-update-form"; // Thymeleaf template for editing medication
        } else {
            model.addAttribute("error", "Medication not found.");
            return "error";
        }
    }

    // POST /doctor-medications/edit/{id} - Update medication information
    @PostMapping("/edit/{id}")
    public String editMedication(@PathVariable("id") long id, @ModelAttribute Medication updatedMedication, Model model) {
        if (updatedMedication.getDoctor() == null || updatedMedication.getPatient() == null) {
            model.addAttribute("error", "Doctor and Patient must be selected.");
            return "doctor-medication-update-form"; // Re-show the form with an error message
        }

        Medication updated = doctorMedicationService.updateMedication(id, updatedMedication);
        if (updated != null) {
            return "redirect:/doctor-medications"; // Redirect after updating
        } else {
            model.addAttribute("error", "Medication not found for update.");
            return "error";
        }
    }

    // POST /doctor-medications/delete/{id} - Delete a medication by ID
    @PostMapping("/delete/{id}")
    public String deleteMedication(@PathVariable("id") long id) {
        boolean deleted = doctorMedicationService.deleteMedicationById(id);
        if (deleted) {
            return "redirect:/doctor-medications"; // Redirect after deletion
        } else {
            return "error"; // Return error page if deletion fails
        }
    }
}
