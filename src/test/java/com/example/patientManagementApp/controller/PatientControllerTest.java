package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Patient;
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

class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private Model model;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients_ShouldReturnPatientsListPage() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        when(patientService.getAllPatients()).thenReturn(patients);

        String viewName = patientController.getAllPatients(model);

        verify(model).addAttribute("patients", patients);
        assertEquals("patients-list", viewName);
    }

    @Test
    void loadAddPatientPage_ShouldReturnAddPatientPage() {
        String viewName = patientController.loadAddPatientPage(model);

        verify(model).addAttribute(eq("patient"), any(Patient.class));
        assertEquals("add-patient", viewName);
    }

    @Test
    void savePatient_ShouldRedirectAfterSaving() {
        Patient patient = new Patient();

        String viewName = patientController.savePatient(patient);

        verify(patientService).savePatient(patient);
        assertEquals("redirect:/patients", viewName);
    }

    @Test
    void loadUpdateForm_ShouldReturnUpdatePatientPage() {
        Patient patient = new Patient();
        when(patientService.getPatientById(1L)).thenReturn(patient);

        String viewName = patientController.loadUpdateForm(1L, model);

        verify(model).addAttribute("patient", patient);
        assertEquals("update-patient", viewName);
    }

    @Test
    void updatePatientInfo_ShouldRedirectAfterUpdating() {
        Patient patient = new Patient();
        patient.setId(1L);

        String viewName = patientController.updatePatientInfo(patient, 1L);

        verify(patientService).updatePatient(patient, 1L);
        assertEquals("redirect:/patients", viewName);
    }

    @Test
    void deletePatient_ShouldRedirectAfterDeleting() {
        String viewName = patientController.deletePatient(1L);

        verify(patientService).deletePatientById(1L);
        assertEquals("redirect:/patients", viewName);
    }

    @Test
    void getPatientDetails_ShouldReturnPatientDetailsPage() {
        Patient patient = new Patient();
        when(patientService.getPatientById(1L)).thenReturn(patient);

        String viewName = patientController.getPatientDetails(1L, model);

        verify(model).addAttribute("patient", patient);
        assertEquals("patient-details", viewName);
    }

    @Test
    void getPatientDetails_PatientNotFound_ShouldReturnErrorPage() {
        when(patientService.getPatientById(1L)).thenReturn(null);

        String viewName = patientController.getPatientDetails(1L, model);

        assertEquals("error", viewName);
    }
}
