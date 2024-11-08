package com.example.patientManagementApp.service.impl;

import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import com.example.patientManagementApp.exception.NoDoctorsOrPatientsException;
import com.example.patientManagementApp.repository.MedicationRepository;
import com.example.patientManagementApp.repository.DoctorRepository;
import com.example.patientManagementApp.repository.PatientRepository;
import com.example.patientManagementApp.service.DoctorMedicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorMedicationServiceImpl implements DoctorMedicationService {

    private final MedicationRepository medicationRepository; // Repository for medication data
    private final DoctorRepository doctorRepository; // Repository for doctor data
    private final PatientRepository patientRepository; // Repository for patient data

    // Constructor-based dependency injection for all repositories
    public DoctorMedicationServiceImpl(MedicationRepository medicationRepository,
                                       DoctorRepository doctorRepository,
                                       PatientRepository patientRepository) {
        this.medicationRepository = medicationRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Retrieves all medications from the repository.
     *
     * @return a list of all medications
     */
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll(); // Retrieves all medications from the repository
    }

    /**
     * Deletes a medication by its ID.
     *
     * @param id the ID of the medication to delete
     * @return true if the medication was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteMedicationById(long id) {
        if (medicationRepository.existsById(id)) {
            medicationRepository.deleteById(id); // Deletes medication by ID if it exists
            return true;
        }
        return false;
    }

    /**
     * Saves a new medication to the repository.
     *
     * @param medication the medication to save
     * @return the saved medication
     */
    @Override
    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication); // Saves a new medication to the repository
    }

    /**
     * Updates an existing medication record.
     *
     * @param id               the ID of the medication to update
     * @param updatedMedication the updated medication data
     * @return the updated medication or null if the medication was not found
     */
    @Override
    public Medication updateMedication(long id, Medication updatedMedication) {
        Optional<Medication> existingMedication = medicationRepository.findById(id);
        if (existingMedication.isPresent()) {
            Medication medication = existingMedication.get();
            // Updating fields with the values from updatedMedication
            medication.setName(updatedMedication.getName());
            medication.setFrequency(updatedMedication.getFrequency());
            medication.setDosage(updatedMedication.getDosage());
            medication.setDoctor(updatedMedication.getDoctor());
            medication.setPatient(updatedMedication.getPatient());

            return medicationRepository.save(medication); // Saves the updated medication
        }
        return null; // Returns null if medication with ID not found
    }

    /**
     * Retrieves a medication by its ID.
     *
     * @param id the ID of the medication
     * @return the medication if found, or null if not found
     */
    @Override
    public Medication getMedicationById(long id) {
        return medicationRepository.findById(id).orElse(null); // Returns the medication by ID or null if not found
    }

    /**
     * Retrieves medications for a specific patient.
     *
     * @param patient the patient whose medications to retrieve
     * @return a list of medications for the specified patient
     */
    @Override
    public List<Medication> getMedicationsByPatient(Patient patient) {
        return medicationRepository.findByPatient(patient); // Retrieves medications for a specific patient
    }

    /**
     * Retrieves medications prescribed by a specific doctor.
     *
     * @param doctor the doctor whose prescribed medications to retrieve
     * @return a list of medications prescribed by the specified doctor
     */
    @Override
    public List<Medication> getMedicationsByDoctor(Doctor doctor) {
        return medicationRepository.findByDoctor(doctor); // Retrieves medications prescribed by a specific doctor
    }

    /**
     * Assigns medication to a patient. This method ensures that there are available doctors and patients
     * before proceeding with the assignment.
     */
    public void assignMedicationToPatient() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Patient> patients = patientRepository.findAll();

        if (doctors.isEmpty() || patients.isEmpty()) {
            throw new NoDoctorsOrPatientsException("No doctors or patients available to assign medication.");
        }

        // Proceed with the medication assignment logic (implementation not shown)
    }
}
