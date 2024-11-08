// PACKAGE
package com.example.patientManagementApp.service;

// IMPORTS
import com.example.patientManagementApp.entity.Patient;

import java.util.List;

/**
 * Interface for defining the services available in this application for managing patient data.
 */
public interface PatientService {

    /**
     * Retrieves a list of all patient records from the database.
     *
     * @return a list of all patients
     */
    List<Patient> getAllPatients();

    /**
     * Retrieves the patient record associated with the specified ID from the database.
     *
     * @param id the ID of the patient to be retrieved
     * @return the patient record with the specified ID
     */
    Patient getPatientById(long id);

    /**
     * Saves a new patient record to the database.
     *
     * @param patient the patient to be saved
     * @return the saved patient record
     */
    Patient savePatient(Patient patient);

    /**
     * Updates the existing patient record with the specified ID using the provided patient data.
     *
     * @param patient the updated patient data
     * @param id the ID of the patient to be updated
     * @return the updated patient record
     */
    Patient updatePatient(Patient patient, long id);

    /**
     * Deletes the patient record associated with the specified ID from the database.
     *
     * @param id the ID of the patient to be deleted
     * @return true if the patient was deleted successfully, false otherwise
     */
    boolean deletePatientById(long id);
}
