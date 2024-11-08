// PACKAGE
package com.example.patientManagementApp.entity;

// IMPORTS
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// ANNOTATIONS
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity // An ANNOTATION used to create a table in the database
@Table(name = "appointments") // An ANNOTATION to specify the table name in the database
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Primary key with auto-generation strategy
    private long id;

    @Column(nullable = false) // Ensure the 'date' field cannot be null
    private LocalDate date;

    @Column(nullable = false) // Ensure the 'time' field cannot be null
    private LocalTime time;

    // Relationship to Patient entity
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading of associated Patient
    @JoinColumn(name = "patient_id", nullable = false) // Foreign key for Patient
    private Patient patient;

    // Relationship to Doctor entity
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading of associated Doctor
    @JoinColumn(name = "doctor_id", nullable = false) // Foreign key for Doctor
    private Doctor doctor;

    @Column(length = 500) // Allow for a maximum of 500 characters for the 'reason'
    private String reason;

    // Custom setters (if necessary) for patient and doctor IDs, but currently they are empty.
    public void setPatientId(long l) {
        // Custom implementation (if any) for setting the patient ID
    }

    public void setDoctorId(long l) {
        // Custom implementation (if any) for setting the doctor ID
    }
}
