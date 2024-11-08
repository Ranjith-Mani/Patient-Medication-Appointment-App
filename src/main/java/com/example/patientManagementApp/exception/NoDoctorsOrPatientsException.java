// PACKAGE
package com.example.patientManagementApp.exception;

/**
 * Custom exception class to handle cases where no doctors or patients are found.
 * This class extends RuntimeException to be thrown during runtime.
 */
public class NoDoctorsOrPatientsException extends RuntimeException {

    /**
     * Constructor that accepts a message to describe the exception.
     *
     * @param message the detail message to explain the exception
     */
    public NoDoctorsOrPatientsException(String message) {
        super(message); // Pass the message to the superclass constructor
    }
}
