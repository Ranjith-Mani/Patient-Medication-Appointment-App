// PACKAGE
package com.example.patientManagementApp.repository;

// IMPORTS
import com.example.patientManagementApp.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ANNOTATION that marks this interface as a DAO (Data Access Object) for data access in Spring
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Check if a doctor with the given email already exists.
     *
     * @param email the email to check
     * @return true if a doctor with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
