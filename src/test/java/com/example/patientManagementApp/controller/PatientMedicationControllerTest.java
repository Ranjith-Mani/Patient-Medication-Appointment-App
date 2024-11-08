// PACKAGE
package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.service.PatientMedicationService;
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
import static org.mockito.Mockito.*;

class PatientMedicationControllerTest {

    @InjectMocks
    private PatientMedicationController patientMedicationController;

    @Mock
    private PatientMedicationService patientMedicationService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMedications() {
        List<Medication> medications = new ArrayList<>();
        when(patientMedicationService.getAllMedications()).thenReturn(medications);

        String view = patientMedicationController.getAllMedications(model);

        assertEquals("patient-medication-list", view);
        verify(model).addAttribute("medications", medications);
    }

    @Test
    void testGetMedicationsByPatient_PatientExists() {
        long patientId = 1L;
        Patient patient = new Patient();
        List<Medication> medications = new ArrayList<>();

        when(patientService.getPatientById(patientId)).thenReturn(patient);
        when(patientMedicationService.getMedicationsByPatient(patient)).thenReturn(medications);

        String view = patientMedicationController.getMedicationsByPatient(patientId, model);

        assertEquals("patient-medication-list", view);
        verify(model).addAttribute("medications", medications);
    }

    @Test
    void testGetMedicationsByPatient_PatientNotFound() {
        long patientId = 1L;
        when(patientService.getPatientById(patientId)).thenReturn(null);

        String view = patientMedicationController.getMedicationsByPatient(patientId, model);

        assertEquals("error", view);
        verify(model).addAttribute("error", "Patient not found.");
    }

    @Test
    void testGetMedicationsByDoctor_DoctorExists() {
        long doctorId = 1L;
        Doctor doctor = new Doctor();
        List<Medication> medications = new ArrayList<>();

        when(doctorService.getDoctorById(doctorId)).thenReturn(doctor);
        when(patientMedicationService.getMedicationsByDoctor(doctor)).thenReturn(medications);

        String view = patientMedicationController.getMedicationsByDoctor(doctorId, model);

        assertEquals("patient-medication-list", view);
        verify(model).addAttribute("medications", medications);
    }

    @Test
    void testGetMedicationsByDoctor_DoctorNotFound() {
        long doctorId = 1L;
        when(doctorService.getDoctorById(doctorId)).thenReturn(null);

        String view = patientMedicationController.getMedicationsByDoctor(doctorId, model);

        assertEquals("error", view);
        verify(model).addAttribute("error", "Doctor not found.");
    }

    @Test
    void testGetMedicationDetails_MedicationExists() {
        long medicationId = 1L;
        Medication medication = new Medication();

        when(patientMedicationService.getMedicationById(medicationId)).thenReturn(medication);

        String view = patientMedicationController.getMedicationDetails(medicationId, model);

        assertEquals("patient-medication-details", view);
        verify(model).addAttribute("medication", medication);
    }

    @Test
    void testGetMedicationDetails_MedicationNotFound() {
        long medicationId = 1L;
        when(patientMedicationService.getMedicationById(medicationId)).thenReturn(null);

        String view = patientMedicationController.getMedicationDetails(medicationId, model);

        assertEquals("error", view);
        verify(model).addAttribute("error", "Medication not found.");
    }

    @Test
    void testAddMedicationForm_DoctorsAndPatientsAvailable() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());

        when(doctorService.getAllDoctors()).thenReturn(doctors);
        when(patientService.getAllPatients()).thenReturn(patients);

        String view = patientMedicationController.addMedicationForm(model);

        assertEquals("patient-medication-form", view);
        verify(model).addAttribute("medication", new Medication());
        verify(model).addAttribute("doctors", doctors);
        verify(model).addAttribute("patients", patients);
    }

    @Test
    void testAddMedicationForm_NoDoctorsOrPatients() {
        when(doctorService.getAllDoctors()).thenReturn(new ArrayList<>());
        when(patientService.getAllPatients()).thenReturn(new ArrayList<>());

        String view = patientMedicationController.addMedicationForm(model);

        assertEquals("error", view);
        verify(model).addAttribute("error", "No doctors or patients available to assign medication.");
    }

    @Test
    void testAddMedication_DoctorAndPatientSelected() {
        Medication medication = new Medication();
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        medication.setDoctor(doctor);
        medication.setPatient(patient);

        String view = patientMedicationController.addMedication(medication, model);

        assertEquals("redirect:/patient-medications", view);
        verify(patientMedicationService).saveMedication(medication);
    }

    @Test
    void testAddMedication_DoctorOrPatientNotSelected() {
        Medication medication = new Medication();

        String view = patientMedicationController.addMedication(medication, model);

        assertEquals("patient-medication-form", view);
        verify(model).addAttribute("error", "Doctor and Patient must be selected.");
    }

    @Test
    void testDeleteMedication_MedicationDeleted() {
        long medicationId = 1L;
        when(patientMedicationService.deleteMedicationById(medicationId)).thenReturn(true);

        String view = patientMedicationController.deleteMedication(medicationId);

        assertEquals("redirect:/patient-medications", view);
    }

    @Test
    void testDeleteMedication_MedicationNotDeleted() {
        long medicationId = 1L;
        when(patientMedicationService.deleteMedicationById(medicationId)).thenReturn(false);

        String view = patientMedicationController.deleteMedication(medicationId);

        assertEquals("error", view);
    }
}
