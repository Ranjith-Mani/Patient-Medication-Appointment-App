package com.example.patientManagementApp.service.impl;

// IMPORTS
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.repository.DoctorRepository;
import com.example.patientManagementApp.repository.PatientRepository;
import com.example.patientManagementApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service implementation for DoctorService interface
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    // Constructor-based dependency injection
    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Retrieves all doctors from the repository.
     *
     * @return a list of all doctors
     */
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll(); // Retrieves all doctor records from the database
    }

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id the ID of the doctor
     * @return the doctor if found, or null if not found
     */
    @Override
    public Doctor getDoctorById(long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null); // Retrieves a doctor by ID or returns null if not found
    }

    /**
     * Saves a new doctor to the repository.
     *
     * @param doctor the doctor to save
     * @return the saved doctor
     * @throws IllegalArgumentException if the email already exists
     */
    @Override
    public Doctor saveDoctor(Doctor doctor) {
        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new IllegalArgumentException("Email already exists."); // Throws exception if email is already taken
        }
        doctorRepository.save(doctor); // Saves a new doctor record
        return doctor;
    }

    /**
     * Updates an existing doctor's details.
     *
     * @param id the ID of the doctor to update
     * @param updatedDoctor the updated doctor data
     * @return the updated doctor if found, or null if the doctor was not found
     */
    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(id);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            // Update fields of existing doctor with the new values
            existingDoctor.setFirstName(updatedDoctor.getFirstName());
            existingDoctor.setLastName(updatedDoctor.getLastName());
            existingDoctor.setEmail(updatedDoctor.getEmail());
            existingDoctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
            existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
            existingDoctor.setHospital(updatedDoctor.getHospital());
            existingDoctor.setAddress(updatedDoctor.getAddress());

            return doctorRepository.save(existingDoctor); // Save updated doctor record
        }
        return null; // Return null if the doctor was not found
    }

    /**
     * Deletes a doctor by their ID.
     *
     * @param id the ID of the doctor to delete
     * @return true if the doctor was successfully deleted, false if not found
     */
    @Override
    public boolean deleteDoctorById(long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id); // Deletes doctor by ID
            return true;
        }
        return false; // Return false if the doctor was not found
    }
}
