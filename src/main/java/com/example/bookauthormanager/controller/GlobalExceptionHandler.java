package com.example.bookauthormanager.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, EntityNotFoundException.class, IllegalArgumentException.class})
    public String handleKnownExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "shared/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Unexpected error occurred: " + ex.getMessage());
        return "shared/error";
    }
}
