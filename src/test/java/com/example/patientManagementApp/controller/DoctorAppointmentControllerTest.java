// PACKAGE
package com.example.patientManagementApp.controller;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.service.DoctorAppointmentService;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorAppointmentController.class)
public class DoctorAppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorAppointmentService doctorAppointmentService;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private PatientService patientService;

    private Appointment testAppointment;

    @BeforeEach
    void setUp() {
        testAppointment = new Appointment();
        testAppointment.setId(1L);
        testAppointment.setDoctorId(1L);
        testAppointment.setPatientId(1L);
        testAppointment.setDate(LocalDate.parse("2024-11-07"));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        // Simulate the service returning a list of appointments
        when(doctorAppointmentService.getAllAppointments()).thenReturn(List.of(testAppointment));

        // Perform GET request to the /doctor-appointments endpoint
        mockMvc.perform(get("/doctor-appointments"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-appointments-list"))
                .andExpect(model().attributeExists("appointments"));
    }

    @Test
    void testShowAddAppointmentForm() throws Exception {
        // Simulate the services for doctors and patients
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));
        when(patientService.getAllPatients()).thenReturn(List.of(new Patient()));

        // Perform GET request to show the form for adding an appointment
        mockMvc.perform(get("/doctor-appointments/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-add-appointment"))
                .andExpect(model().attributeExists("appointment"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testSaveAppointment() throws Exception {
        // Simulate a successful save
        when(doctorAppointmentService.saveAppointment(Mockito.any(Appointment.class))).thenReturn(testAppointment);

        // Perform POST request to save the appointment
        mockMvc.perform(post("/doctor-appointments/save")
                        .flashAttr("appointment", testAppointment))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor-appointments"));
    }

    @Test
    void testLoadUpdateForm() throws Exception {
        // Simulate finding an appointment by ID
        when(doctorAppointmentService.getAppointmentById(1L)).thenReturn(testAppointment);
        when(doctorService.getAllDoctors()).thenReturn(List.of(new Doctor()));
        when(patientService.getAllPatients()).thenReturn(List.of(new Patient()));

        // Perform GET request to load the update form
        mockMvc.perform(get("/doctor-appointments/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-update-appointment"))
                .andExpect(model().attributeExists("appointment"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testUpdateAppointmentInfo() throws Exception {
        // Simulate an appointment update
        when(doctorAppointmentService.updateAppointment(eq(1L), Mockito.any(Appointment.class))).thenReturn(testAppointment);

        // Perform POST request to update the appointment
        mockMvc.perform(post("/doctor-appointments/update/1")
                        .flashAttr("appointment", testAppointment))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor-appointments"));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        // Simulate the deletion of an appointment
        doNothing().when(doctorAppointmentService).deleteAppointmentById(1L);

        // Perform POST request to delete the appointment
        mockMvc.perform(post("/doctor-appointments/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor-appointments"));
    }

    @Test
    void testGetAppointmentDetails() throws Exception {
        // Simulate the service returning an appointment
        when(doctorAppointmentService.getAppointmentById(1L)).thenReturn(testAppointment);

        // Perform GET request to view appointment details
        mockMvc.perform(get("/doctor-appointments/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-appointment-details"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testAppointmentNotFound() throws Exception {
        // Simulate that the appointment is not found
        when(doctorAppointmentService.getAppointmentById(999L)).thenReturn(null);

        // Perform GET request to view details of a non-existing appointment
        mockMvc.perform(get("/doctor-appointments/details/999"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage", "Appointment not found!"));
    }
}
