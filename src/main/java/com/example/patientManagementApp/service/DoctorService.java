// PACKAGE
package com.example.patientManagementApp.service;

// IMPORTS
import com.example.patientManagementApp.entity.Doctor;

import java.util.List;

/**
 * Interface for defining the services available for managing doctors in this application.
 */
public interface DoctorService {

    /**
     * Retrieves a list of all doctor records from the database.
     *
     * @return a list of all doctors
     */
    List<Doctor> getAllDoctors();

    /**
     * Retrieves the doctor record associated with the specified ID from the database.
     *
     * @param id the ID of the doctor to be retrieved
     * @return the doctor record with the given ID
     */
    Doctor getDoctorById(long id);

    /**
     * Saves a new doctor record to the database.
     *
     * @param doctor the doctor to be saved
     * @return the saved doctor record
     */
    Doctor saveDoctor(Doctor doctor);

    /**
     * Updates the existing doctor record in the Doctor List.
     *
     * @param id the ID of the doctor to be updated
     * @param updatedDoctor the updated doctor data
     * @return the updated doctor record
     */
    Doctor updateDoctor(Long id, Doctor updatedDoctor);

    /**
     * Deletes the doctor record associated with the specified ID from the database.
     *
     * @param id the ID of the doctor to be deleted
     * @return true if the doctor was deleted successfully, false otherwise
     */
    boolean deleteDoctorById(long id);
}
