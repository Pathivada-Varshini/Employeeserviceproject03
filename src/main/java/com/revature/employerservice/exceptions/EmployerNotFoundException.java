package com.revature.employerservice.exceptions;

public class EmployerNotFoundException extends RuntimeException {
    public EmployerNotFoundException(String message) {
        super(message);
    }
}
