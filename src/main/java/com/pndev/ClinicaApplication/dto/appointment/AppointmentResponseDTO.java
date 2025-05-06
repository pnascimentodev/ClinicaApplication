package com.pndev.ClinicaApplication.dto.appointment;

import com.pndev.ClinicaApplication.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id,
        LocalDateTime dateTime,
        String doctorName,
        String patientName,
        AppointmentStatus status
)
{ }
