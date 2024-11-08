package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.service.DoctorMedicationService;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DoctorMedicationControllerTest {

    @Mock
    private DoctorMedicationService doctorMedicationService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @Mock
    private Model model;

    @InjectMocks
    private DoctorMedicationController doctorMedicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMedications_ShouldReturnMedicationListPage() {
        List<Medication> medications = new ArrayList<>();
        when(doctorMedicationService.getAllMedications()).thenReturn(medications);

        String viewName = doctorMedicationController.getAllMedications(model);

        verify(model).addAttribute("medications", medications);
        assertEquals("doctor-medication-list", viewName);
    }

    @Test
    void getMedicationsByPatient_ShouldReturnMedicationListForPatient() {
        Patient patient = new Patient();
        when(patientService.getPatientById(1L)).thenReturn(patient);
        List<Medication> medications = new ArrayList<>();
        when(doctorMedicationService.getMedicationsByPatient(patient)).thenReturn(medications);

        String viewName = doctorMedicationController.getMedicationsByPatient(1L, model);

        verify(model).addAttribute("medications", medications);
        assertEquals("doctor-medication-list", viewName);
    }

    @Test
    void getMedicationsByDoctor_ShouldReturnMedicationListForDoctor() {
        Doctor doctor = new Doctor();
        when(doctorService.getDoctorById(1L)).thenReturn(doctor);
        List<Medication> medications = new ArrayList<>();
        when(doctorMedicationService.getMedicationsByDoctor(doctor)).thenReturn(medications);

        String viewName = doctorMedicationController.getMedicationsByDoctor(1L, model);

        verify(model).addAttribute("medications", medications);
        assertEquals("doctor-medication-list", viewName);
    }

    @Test
    void getMedicationDetails_ShouldReturnMedicationDetailsPage() {
        Medication medication = new Medication();
        when(doctorMedicationService.getMedicationById(1L)).thenReturn(medication);

        String viewName = doctorMedicationController.getMedicationDetails(1L, model);

        verify(model).addAttribute("medication", medication);
        assertEquals("doctor-medication-details", viewName);
    }

    @Test
    void addMedicationForm_ShouldReturnAddMedicationForm() {
        List<Doctor> doctors = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        when(patientService.getAllPatients()).thenReturn(patients);

        String viewName = doctorMedicationController.addMedicationForm(model);

        verify(model).addAttribute("medication", any(Medication.class));
        verify(model).addAttribute("doctors", doctors);
        verify(model).addAttribute("patients", patients);
        assertEquals("doctor-medication-form", viewName);
    }

    @Test
    void addMedication_ShouldRedirectAfterSaving() {
        Medication medication = new Medication();
        medication.setDoctor(new Doctor());
        medication.setPatient(new Patient());

        String viewName = doctorMedicationController.addMedication(medication, model);

        verify(doctorMedicationService).saveMedication(medication);
        assertEquals("redirect:/doctor-medications", viewName);
    }

    @Test
    void editMedicationForm_ShouldReturnEditMedicationForm() {
        Medication medication = new Medication();
        when(doctorMedicationService.getMedicationById(1L)).thenReturn(medication);
        List<Doctor> doctors = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        when(doctorService.getAllDoctors()).thenReturn(doctors);
        when(patientService.getAllPatients()).thenReturn(patients);

        String viewName = doctorMedicationController.editMedicationForm(1L, model);

        verify(model).addAttribute("medication", medication);
        verify(model).addAttribute("doctors", doctors);
        verify(model).addAttribute("patients", patients);
        assertEquals("doctor-medication-update-form", viewName);
    }

    @Test
    void editMedication_ShouldRedirectAfterUpdating() {
        Medication updatedMedication = new Medication();
        updatedMedication.setDoctor(new Doctor());
        updatedMedication.setPatient(new Patient());
        when(doctorMedicationService.updateMedication(1L, updatedMedication)).thenReturn(updatedMedication);

        String viewName = doctorMedicationController.editMedication(1L, updatedMedication, model);

        verify(doctorMedicationService).updateMedication(1L, updatedMedication);
        assertEquals("redirect:/doctor-medications", viewName);
    }

    @Test
    void deleteMedication_ShouldRedirectAfterDeleting() {
        when(doctorMedicationService.deleteMedicationById(1L)).thenReturn(true);

        String viewName = doctorMedicationController.deleteMedication(1L);

        verify(doctorMedicationService).deleteMedicationById(1L);
        assertEquals("redirect:/doctor-medications", viewName);
    }
}
