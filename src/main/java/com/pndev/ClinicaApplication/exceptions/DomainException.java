package com.pndev.ClinicaApplication.exceptions;

public class DomainException extends RuntimeException {

    public enum ErrorType{
        USER_NOT_FOUND,
        APPOINTMENT_NOT_FOUND,
            DOCTOR_NOT_FOUND,
        PATIENT_NOT_FOUND,
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
