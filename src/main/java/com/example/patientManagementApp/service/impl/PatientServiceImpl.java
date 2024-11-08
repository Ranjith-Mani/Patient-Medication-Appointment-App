// PACKAGE
package com.example.patientManagementApp.service.impl;

// IMPORTS
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.repository.PatientRepository;
import com.example.patientManagementApp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service implementation for PatientService interface
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    // Constructor-based dependency injection for PatientRepository
    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Retrieves all patient records from the database.
     *
     * @return a list of all patients
     */
    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll(); // Retrieves all patients from the repository
    }

    /**
     * Retrieves a patient by ID or returns null if not found.
     *
     * @param id the ID of the patient
     * @return the patient if found, or null if not found
     */
    @Override
    public Patient getPatientById(long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null); // Returns the patient if found, or null if not
    }

    /**
     * Saves a new patient record to the database.
     *
     * @param patient the patient to save
     * @return the saved patient
     */
    @Override
    public Patient savePatient(Patient patient) {
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new IllegalArgumentException("Email already exists."); // Throws error if email already exists
        }
        return patientRepository.save(patient); // Saves a new patient record
    }

    /**
     * Updates an existing patient record with the provided data.
     *
     * @param updatedPatient the updated patient data
     * @param id the ID of the patient to update
     * @return the updated patient if found and updated, or null if not found
     */
    @Override
    public Patient updatePatient(Patient updatedPatient, long id) {
        Optional<Patient> existingPatientOpt = patientRepository.findById(id);
        if (existingPatientOpt.isPresent()) {
            Patient existingPatient = existingPatientOpt.get();
            // Update fields of the existing patient with the new values
            existingPatient.setFirstName(updatedPatient.getFirstName());
            existingPatient.setLastName(updatedPatient.getLastName());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
            existingPatient.setAddress(updatedPatient.getAddress());
            return patientRepository.save(existingPatient); // Save updated patient record
        }
        return null; // Returns null if the patient was not found
    }

    /**
     * Deletes the patient by ID.
     *
     * @param id the ID of the patient to delete
     * @return true if the patient was deleted, false if not found
     */
    @Override
    public boolean deletePatientById(long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id); // Deletes the patient by ID
            return true; // Returns true if deletion was successful
        }
        return false; // Returns false if the patient was not found
    }
}
