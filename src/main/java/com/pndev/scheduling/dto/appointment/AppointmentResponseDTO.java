package com.pndev.scheduling.dto.appointment;

import com.pndev.scheduling.model.Appointment;
import com.pndev.scheduling.model.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentResponseDTO(
        Long id,
        LocalDateTime dateTime,
        String doctorName,
        String patientName,
        AppointmentStatus status
)
{
    public static AppointmentResponseDTO toDto(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDateTime(),
                appointment.getDoctor().getUser().getName(),
                appointment.getPatient().getUser().getName(),
                appointment.getStatus()
        );
    }

    public static List<AppointmentResponseDTO> toDtoList(List<Appointment> appointments) {
        return List.of();
    }
}
