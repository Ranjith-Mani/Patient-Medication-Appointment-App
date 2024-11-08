// PACKAGE
package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.service.PatientMedicationService;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient-medications")
public class PatientMedicationController {

    private final PatientMedicationService patientMedicationService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Constructor injection for services
    public PatientMedicationController(PatientMedicationService patientMedicationService,
                                       DoctorService doctorService,
                                       PatientService patientService) {
        this.patientMedicationService = patientMedicationService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // GET /patient-medications - List all medications
    @GetMapping
    public String getAllMedications(Model model) {
        List<Medication> medications = patientMedicationService.getAllMedications();
        model.addAttribute("medications", medications);
        return "patient-medication-list"; // Thymeleaf template for listing medications
    }

    // GET /patient-medications/patient/{patientId} - List medications for a specific patient
    @GetMapping("/patient/{patientId}")
    public String getMedicationsByPatient(@PathVariable("patientId") long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            model.addAttribute("error", "Patient not found.");
            return "error"; // Custom error template
        }
        List<Medication> medications = patientMedicationService.getMedicationsByPatient(patient);
        model.addAttribute("medications", medications);
        return "patient-medication-list";
    }

    // GET /patient-medications/doctor/{doctorId} - List medications prescribed by a specific doctor
    @GetMapping("/doctor/{doctorId}")
    public String getMedicationsByDoctor(@PathVariable("doctorId") long doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            model.addAttribute("error", "Doctor not found.");
            return "error"; // Custom error template
        }
        List<Medication> medications = patientMedicationService.getMedicationsByDoctor(doctor);
        model.addAttribute("medications", medications);
        return "patient-medication-list";
    }

    // GET /patient-medications/details/{id} - View details of a specific medication
    @GetMapping("/details/{id}")
    public String getMedicationDetails(@PathVariable("id") long id, Model model) {
        Medication medication = patientMedicationService.getMedicationById(id);
        if (medication != null) {
            model.addAttribute("medication", medication);
            return "patient-medication-details"; // Thymeleaf template for medication details
        } else {
            model.addAttribute("error", "Medication not found.");
            return "error";
        }
    }

    // GET /patient-medications/add - Load add medication form
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

        return "patient-medication-form"; // Thymeleaf template for adding medication
    }

    // POST /patient-medications/add - Save a new medication
    @PostMapping("/add")
    public String addMedication(@ModelAttribute Medication medication, Model model) {
        if (medication.getDoctor() == null || medication.getPatient() == null) {
            model.addAttribute("error", "Doctor and Patient must be selected.");
            return "patient-medication-form"; // Re-show the form with an error message
        }

        patientMedicationService.saveMedication(medication);
        return "redirect:/patient-medications"; // Redirect after saving
    }

    // GET /patient-medications/edit/{id} - Load edit medication form
    @GetMapping("/edit/{id}")
    public String editMedicationForm(@PathVariable("id") long id, Model model) {
        Medication medication = patientMedicationService.getMedicationById(id);
        if (medication != null) {
            List<Doctor> doctors = doctorService.getAllDoctors();
            List<Patient> patients = patientService.getAllPatients();

            model.addAttribute("medication", medication);
            model.addAttribute("doctors", doctors);
            model.addAttribute("patients", patients);
            return "patient-medication-update-form"; // Thymeleaf template for editing medication
        } else {
            model.addAttribute("error", "Medication not found.");
            return "error";
        }
    }

    // POST /patient-medications/edit/{id} - Update medication information
    @PostMapping("/edit/{id}")
    public String editMedication(@PathVariable("id") long id, @ModelAttribute Medication updatedMedication, Model model) {
        if (updatedMedication.getDoctor() == null || updatedMedication.getPatient() == null) {
            model.addAttribute("error", "Doctor and Patient must be selected.");
            return "patient-medication-update-form"; // Re-show the form with an error message
        }

        Medication updated = patientMedicationService.updateMedication(id, updatedMedication);
        if (updated != null) {
            return "redirect:/patient-medications"; // Redirect after updating
        } else {
            model.addAttribute("error", "Medication not found for update.");
            return "error";
        }
    }

    // POST /patient-medications/delete/{id} - Delete a medication by ID
    @PostMapping("/delete/{id}")
    public String deleteMedication(@PathVariable("id") long id) {
        boolean deleted = patientMedicationService.deleteMedicationById(id);
        if (deleted) {
            return "redirect:/patient-medications"; // Redirect after deletion
        } else {
            return "error"; // Return error page if deletion fails
        }
    }
}
