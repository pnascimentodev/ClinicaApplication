package com.pndev.scheduling.dto.patient;

import com.pndev.scheduling.model.Patient;

public record PatientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        String phone
) {
    public static PatientResponseDTO toDto(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getUser().getName(),
                patient.getUser().getEmail(),
                patient.getCpf(),
                patient.getPhone()
        );
    }
}
