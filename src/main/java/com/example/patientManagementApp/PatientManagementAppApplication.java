package com.example.patientManagementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Patient Management Application.
 * This class serves as the entry point to start the Spring Boot application.
 */
@SpringBootApplication
public class PatientManagementAppApplication {

	/**
	 * The main method to run the application.
	 * It initializes and runs the Spring Boot application.
	 *
	 * @param args command-line arguments (if any)
	 */
	public static void main(String[] args) {
		// Running the Spring Boot application
		SpringApplication.run(PatientManagementAppApplication.class, args);
	}
}
