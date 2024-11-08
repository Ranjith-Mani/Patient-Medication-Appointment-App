// PACKAGE
package com.example.patientManagementApp.service;

// IMPORTS
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;

import java.util.List;

/**
 * Interface for defining the services available for patient medication management.
 */
public interface PatientMedicationService {

    /**
     * Retrieves a list of all medication records from the database.
     *
     * @return a list of all medications
     */
    List<Medication> getAllMedications();

    /**
     * Deletes the medication record associated with the specified ID from the database.
     *
     * @param id the ID of the medication to be deleted
     * @return true if the medication was deleted successfully, false otherwise
     */
    boolean deleteMedicationById(long id);

    /**
     * Saves a new medication record to the database.
     *
     * @param medication the medication to be saved
     * @return the saved medication record
     */
    Medication saveMedication(Medication medication);

    /**
     * Updates an existing medication record.
     *
     * @param id the ID of the medication to be updated
     * @param updatedMedication the updated medication data
     * @return the updated medication record
     */
    Medication updateMedication(long id, Medication updatedMedication);

    /**
     * Retrieves the medication record associated with the specified ID from the database.
     *
     * @param id the ID of the medication to be retrieved
     * @return the medication record with the given ID
     */
    Medication getMedicationById(long id);

    /**
     * Retrieves medications for a specific patient.
     *
     * @param patient the patient whose medications are to be retrieved
     * @return a list of medications for the specified patient
     */
    List<Medication> getMedicationsByPatient(Patient patient);

    /**
     * Retrieves medications prescribed by a specific doctor.
     *
     * @param doctor the doctor who prescribed the medications
     * @return a list of medications prescribed by the specified doctor
     */
    List<Medication> getMedicationsByDoctor(Doctor doctor);
}
