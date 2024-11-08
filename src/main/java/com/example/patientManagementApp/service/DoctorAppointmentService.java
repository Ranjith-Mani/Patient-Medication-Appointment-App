// PACKAGE
package com.example.patientManagementApp.service;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Patient;

import java.util.List;

/**
 * Defines the services available for managing doctor appointments in this application.
 */
public interface DoctorAppointmentService {

    /**
     * Retrieves a list of all appointment records from the database.
     *
     * @return a list of all appointments
     */
    List<Appointment> getAllAppointments();

    /**
     * Deletes the appointment record associated with the specified ID from the database.
     *
     * @param id the ID of the appointment to be deleted
     * @return true if the appointment was deleted successfully, false otherwise
     */
    boolean deleteAppointmentById(long id);

    /**
     * Saves a new appointment record to the database.
     *
     * @param appointment the appointment to be saved
     * @return the saved appointment record
     */
    Appointment saveAppointment(Appointment appointment);

    /**
     * Updates an existing appointment record.
     *
     * @param id the ID of the appointment to be updated
     * @param updatedAppointment the updated appointment data
     * @return the updated appointment record
     */
    Appointment updateAppointment(long id, Appointment updatedAppointment);

    /**
     * Retrieves the appointment record associated with the specified ID from the database.
     *
     * @param id the ID of the appointment to be retrieved
     * @return the appointment record with the given ID
     */
    Appointment getAppointmentById(long id);

    /**
     * Retrieves appointments for a specific patient.
     *
     * @param patient the patient whose appointments are to be retrieved
     * @return a list of appointments for the given patient
     */
    List<Appointment> getAppointmentsByPatient(Patient patient);

    /**
     * Retrieves appointments with a specific doctor.
     *
     * @param doctor the doctor whose appointments are to be retrieved
     * @return a list of appointments with the given doctor
     */
    List<Appointment> getAppointmentsByDoctor(Doctor doctor);
}
