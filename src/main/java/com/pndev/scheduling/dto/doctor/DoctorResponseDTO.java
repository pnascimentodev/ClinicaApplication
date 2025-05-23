package com.pndev.scheduling.dto.doctor;

import com.pndev.scheduling.model.Doctor;

public record DoctorResponseDTO(
        Long id,
        String name,
        String email,
        String crm,
        String specialty
) {
    public static DoctorResponseDTO toDto(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getUser().getName(),
                doctor.getUser().getEmail(),
                doctor.getCrm(),
                doctor.getSpecialty()
        );
    }
}