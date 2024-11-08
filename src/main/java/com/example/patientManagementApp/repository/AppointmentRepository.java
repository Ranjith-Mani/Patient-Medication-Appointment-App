// PACKAGE
package com.example.patientManagementApp.repository;

// IMPORTS
import com.example.patientManagementApp.entity.Appointment;
import com.example.patientManagementApp.entity.Doctor;
import com.example.patientManagementApp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ANNOTATION
@Repository // Marks this interface as a DAO (Data Access Object) for Spring Data JPA
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Find all appointments scheduled for a specific patient.
     *
     * @param patient the patient for whom appointments need to be fetched
     * @return a list of appointments for the given patient
     */
    List<Appointment> findByPatient(Patient patient);

    /**
     * Find all appointments scheduled with a specific doctor.
     *
     * @param doctor the doctor for whom appointments need to be fetched
     * @return a list of appointments with the given doctor
     */
    List<Appointment> findByDoctor(Doctor doctor);
}
