// PACKAGE
package com.example.patientManagementApp.controller;

// IMPORTS
import com.example.patientManagementApp.exception.NoDoctorsOrPatientsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle all exceptions globally
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage()); // Add the exception message to the model
        return "error"; // Forward to the error.html template
    }

    // Specific handler for NoDoctorsOrPatientsException
    @ExceptionHandler(NoDoctorsOrPatientsException.class)
    public String handleNoDoctorsOrPatientsException(NoDoctorsOrPatientsException ex, Model model) {
        model.addAttribute("errorMessage", "No doctors or patients available to assign medication.");
        return "error"; // Forward to error.html
    }
}
