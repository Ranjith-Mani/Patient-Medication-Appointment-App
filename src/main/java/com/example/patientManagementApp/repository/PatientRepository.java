// PACKAGE
package com.example.patientManagementApp.repository;

// IMPORTS
import com.example.patientManagementApp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ANNOTATION
// Marks this interface as a DAO (Data Access Object) for data access in Spring
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Check if a patient with the given email already exists.
     *
     * @param email the email to check
     * @return true if a patient with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
