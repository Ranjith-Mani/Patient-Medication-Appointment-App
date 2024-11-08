// PACKAGE
package com.example.patientManagementApp.service.impl;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.repository.AppointmentRepository;
import com.example.patientManagementApp.service.PatientAppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ANNOTATION
@Service // Marks this class as a service component in Spring
public class PatientAppointmentServiceImpl implements PatientAppointmentService {

    private final AppointmentRepository appointmentRepository;

    // Constructor for dependency injection
    public PatientAppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Retrieves all appointments from the repository.
     *
     * @return a list of all appointments
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll(); // Retrieves all appointments from the repository
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id the ID of the appointment to delete
     * @return true if the appointment was deleted, false if not found
     */
    @Override
    @Transactional // Ensures deletion is handled in a transactional context
    public boolean deleteAppointmentById(long id) {
        if (appointmentRepository.existsById(id)) { // Checks if the appointment exists before attempting deletion
            appointmentRepository.deleteById(id);
            return true; // Returns true if deletion was successful
        }
        return false; // Returns false if the appointment was not found
    }

    /**
     * Saves a new appointment to the repository.
     *
     * @param appointment the appointment to save
     * @return the saved appointment
     */
    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment); // Saves a new appointment record to the repository
    }

    /**
     * Updates an existing appointment.
     *
     * @param id the ID of the appointment to update
     * @param updatedAppointment the updated appointment data
     * @return the updated appointment, or null if not found
     */
    @Override
    public Appointment updateAppointment(long id, Appointment updatedAppointment) {
        return appointmentRepository.findById(id).map(existingAppointment -> {
            // Updates fields of the existing appointment
            existingAppointment.setDate(updatedAppointment.getDate());
            existingAppointment.setTime(updatedAppointment.getTime());
            existingAppointment.setReason(updatedAppointment.getReason());
            existingAppointment.setPatient(updatedAppointment.getPatient()); // Updates associated patient
            existingAppointment.setDoctor(updatedAppointment.getDoctor()); // Updates associated doctor
            return appointmentRepository.save(existingAppointment); // Saves the updated appointment
        }).orElse(null); // Returns null if the appointment was not found for update
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param id the ID of the appointment to retrieve
     * @return the appointment if found, or null if not found
     */
    @Override
    public Appointment getAppointmentById(long id) {
        return appointmentRepository.findById(id).orElse(null); // Returns the appointment if found, or null
    }

    /**
     * Retrieves appointments by a specific patient.
     *
     * @param patient the patient whose appointments to retrieve
     * @return a list of appointments for the given patient
     */
    @Override
    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient); // Retrieves appointments for a specific patient
    }

    /**
     * Retrieves appointments by a specific doctor.
     *
     * @param doctor the doctor whose appointments to retrieve
     * @return a list of appointments with the given doctor
     */
    @Override
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor); // Retrieves appointments with a specific doctor
    }
}
