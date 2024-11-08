package com.example.patientManagementApp.controller;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.service.DoctorService;
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

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private Model model;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDoctors_ShouldReturnDoctorsListPage() {
        // Prepare test data
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());

        // Mock service method
        when(doctorService.getAllDoctors()).thenReturn(doctors);

        // Call method to be tested
        String viewName = doctorController.getAllDoctors(model);

        // Verify service interaction and check view name
        verify(model).addAttribute("doctors", doctors);
        assertEquals("doctors-list", viewName);
    }

    @Test
    void loadAddDoctorPage_ShouldReturnAddDoctorPage() {
        // Call method to be tested
        String viewName = doctorController.loadAddDoctorPage(model);

        // Verify model interaction and check view name
        verify(model).addAttribute(eq("doctor"), any(Doctor.class));
        assertEquals("add-doctor", viewName);
    }

    @Test
    void saveDoctor_ShouldRedirectAfterSaving() {
        // Prepare test data
        Doctor doctor = new Doctor();

        // Call method to be tested
        String viewName = doctorController.saveDoctor(doctor);

        // Verify service method and check view name
        verify(doctorService).saveDoctor(doctor);
        assertEquals("redirect:/doctors", viewName);
    }

    @Test
    void loadUpdateForm_ShouldReturnUpdateDoctorPage() {
        // Prepare test data
        Doctor doctor = new Doctor();

        // Mock service method
        when(doctorService.getDoctorById(1L)).thenReturn(doctor);

        // Call method to be tested
        String viewName = doctorController.loadUpdateForm(1L, model);

        // Verify model interaction and check view name
        verify(model).addAttribute("doctor", doctor);
        assertEquals("update-doctor", viewName);
    }

    @Test
    void updateDoctorInfo_ShouldRedirectAfterUpdating() {
        // Prepare test data
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        // Call method to be tested
        String viewName = doctorController.updateDoctorInfo(doctor, 1L);

        // Verify service method and check view name
        verify(doctorService).updateDoctor(1L, doctor);
        assertEquals("redirect:/doctors", viewName);
    }

    @Test
    void deleteDoctor_ShouldRedirectAfterDeleting() {
        // Call method to be tested
        String viewName = doctorController.deleteDoctor(1L);

        // Verify service method and check view name
        verify(doctorService).deleteDoctorById(1L);
        assertEquals("redirect:/doctors", viewName);
    }

    @Test
    void getDoctorDetails_ShouldReturnDoctorDetailsPage() {
        // Prepare test data
        Doctor doctor = new Doctor();

        // Mock service method
        when(doctorService.getDoctorById(1L)).thenReturn(doctor);

        // Call method to be tested
        String viewName = doctorController.getDoctorDetails(1L, model);

        // Verify model interaction and check view name
        verify(model).addAttribute("doctor", doctor);
        assertEquals("doctor-details", viewName);
    }

    @Test
    void getDoctorDetails_DoctorNotFound_ShouldReturnErrorPage() {
        // Mock service method for non-existent doctor
        when(doctorService.getDoctorById(1L)).thenReturn(null);

        // Call method to be tested
        String viewName = doctorController.getDoctorDetails(1L, model);

        // Check view name
        assertEquals("error", viewName);
    }
}
