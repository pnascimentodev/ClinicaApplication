package com.pndev.ClinicaApplication.dto.patient;

public record PatientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        String phone
) {
}
