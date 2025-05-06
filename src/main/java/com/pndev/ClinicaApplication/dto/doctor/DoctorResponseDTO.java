package com.pndev.ClinicaApplication.dto.doctor;

public record DoctorResponseDTO(
        Long id,
        String name,
        String email,
        String crm,
        String specialty
) {
}
