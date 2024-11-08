// PACKAGE
package com.example.patientManagementApp.controller;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.service.DoctorService;
import com.example.patientManagementApp.service.PatientAppointmentService;
import com.example.patientManagementApp.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(PatientAppointmentController.class) // This focuses on testing only the controller layer
@ExtendWith(MockitoExtension.class) // To use Mockito annotations for mocking
public class PatientAppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc to simulate HTTP requests and test the controller

    @Mock
    private PatientAppointmentService patientAppointmentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientAppointmentController patientAppointmentController; // Inject mocked services into the controller

    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(null); // Set a mock doctor if needed
        appointment.setPatient(null); // Set a mock patient if needed
        appointment.setDate(null); // Set a date if required
    }

    @Test
    public void testGetAllAppointments() throws Exception {
        when(patientAppointmentService.getAllAppointments()).thenReturn(Collections.singletonList(appointment));

        mockMvc.perform(MockMvcRequestBuilders.get("/patient-appointments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient-appointments-list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appointments"));
    }

    @Test
    public void testShowAddAppointmentForm() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList()); // Mock empty list if needed
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList()); // Mock empty list if needed

        mockMvc.perform(MockMvcRequestBuilders.get("/patient-appointments/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient-add-appointment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appointment", "doctors", "patients"));
    }

    @Test
    public void testSaveAppointment() throws Exception {
        when(patientAppointmentService.saveAppointment(appointment)).thenReturn(appointment); // Mock service call

        mockMvc.perform(MockMvcRequestBuilders.post("/patient-appointments/save")
                        .flashAttr("appointment", appointment)) // Send the appointment as a model attribute
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient-appointments"));
    }

    @Test
    public void testLoadUpdateForm() throws Exception {
        when(patientAppointmentService.getAppointmentById(1L)).thenReturn(appointment); // Mock the existing appointment

        mockMvc.perform(MockMvcRequestBuilders.get("/patient-appointments/update/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient-update-appointment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appointment", "doctors", "patients"));
    }

    @Test
    public void testUpdateAppointmentInfo() throws Exception {
        when(patientAppointmentService.getAppointmentById(1L)).thenReturn(appointment);
        when(patientAppointmentService.updateAppointment(1L, appointment)).thenReturn(appointment);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient-appointments/update/{id}", 1L)
                        .flashAttr("appointment", appointment)) // Send updated appointment data
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient-appointments"));
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/patient-appointments/delete/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient-appointments"));
    }

    @Test
    public void testGetAppointmentDetails() throws Exception {
        when(patientAppointmentService.getAppointmentById(1L)).thenReturn(appointment);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient-appointments/details/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient-appointment-details"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("appointment"));
    }
}
