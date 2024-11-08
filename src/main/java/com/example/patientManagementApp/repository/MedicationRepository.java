// PACKAGE
package com.example.patientManagementApp.repository;

// IMPORTS
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Medication;
import com.example.patientManagementApp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ANNOTATION
// Marks this interface as a DAO (Data Access Object) for Spring Data JPA
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    /**
     * Find all medications prescribed to a specific patient.
     *
     * @param patient the patient for whom medications need to be fetched
     * @return a list of medications prescribed to the given patient
     */
    List<Medication> findByPatient(Patient patient);

    /**
     * Find all medications prescribed by a specific doctor.
     *
     * @param doctor the doctor who prescribed the medications
     * @return a list of medications prescribed by the given doctor
     */
    List<Medication> findByDoctor(Doctor doctor);
}
