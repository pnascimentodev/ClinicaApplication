package com.pndev.ClinicaApplication.exceptions;

public class DomainException extends RuntimeException {

    public enum ErrorType{
        USER_NOT_FOUND,
        APPOINTMENT_NOT_FOUND,
        DOCTOR_NOT_FOUND,
        PATIENT_NOT_FOUND,
        USER_ALREADY_EXISTS,
        DOCTOR_ALREADY_EXISTS,
        PATIENT_ALREADY_EXISTS
    }

    private final ErrorType errorType;

    public DomainException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
