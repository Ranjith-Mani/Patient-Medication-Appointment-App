// PACKAGE
package com.example.patientManagementApp.service.impl;

// IMPORTS
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.repository.MedicationRepository;
import com.example.patientManagementApp.service.PatientMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service Implementation for managing patient medication
@Service
public class PatientMedicationServiceImpl implements PatientMedicationService {

    // Medication repository for accessing the database
    private final MedicationRepository medicationRepository;

    // Constructor for dependency injection
    @Autowired
    public PatientMedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    /**
     * Retrieves all medications from the repository.
     *
     * @return a list of all medications
     */
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll(); // Retrieves all medications
    }

    /**
     * Deletes a medication by its ID.
     *
     * @param id the ID of the medication to delete
     * @return true if the medication was deleted, false if not found
     */
    @Override
    public boolean deleteMedicationById(long id) {
        if (medicationRepository.existsById(id)) {
            medicationRepository.deleteById(id); // Deletes the medication by ID
            return true; // Returns true if deletion was successful
        }
        return false; // Returns false if the medication was not found
    }

    /**
     * Saves a new medication to the repository.
     *
     * @param medication the medication to save
     * @return the saved medication
     */
    @Override
    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication); // Saves a new medication record
    }

    /**
     * Updates an existing medication.
     *
     * @param id the ID of the medication to update
     * @param updatedMedication the updated medication data
     * @return the updated medication, or null if not found
     */
    @Override
    public Medication updateMedication(long id, Medication updatedMedication) {
        Optional<Medication> existingMedication = medicationRepository.findById(id);
        if (existingMedication.isPresent()) {
            updatedMedication.setId(id); // Ensures the ID remains unchanged
            return medicationRepository.save(updatedMedication); // Saves the updated medication
        }
        return null; // Returns null if the medication was not found for update
    }

    /**
     * Retrieves a medication by its ID.
     *
     * @param id the ID of the medication to retrieve
     * @return the medication if found, or null if not found
     */
    @Override
    public Medication getMedicationById(long id) {
        return medicationRepository.findById(id).orElse(null); // Retrieves the medication if found, or null
    }

    /**
     * Retrieves medications for a specific patient.
     *
     * @param patient the patient whose medications to retrieve
     * @return a list of medications for the given patient
     */
    @Override
    public List<Medication> getMedicationsByPatient(Patient patient) {
        return medicationRepository.findByPatient(patient); // Retrieves medications for a specific patient
    }

    /**
     * Retrieves medications prescribed by a specific doctor.
     *
     * @param doctor the doctor whose prescribed medications to retrieve
     * @return a list of medications prescribed by the given doctor
     */
    @Override
    public List<Medication> getMedicationsByDoctor(Doctor doctor) {
        return medicationRepository.findByDoctor(doctor); // Retrieves medications prescribed by a specific doctor
    }
}
