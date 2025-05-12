package com.pndev.ClinicaApplication.dto.doctor;

import com.pndev.ClinicaApplication.model.Doctor;

public record DoctorResponseDTO(
        Long id,
        String name,
        String email,
        String crm,
        String specialty
) {
    private DoctorResponseDTO  toDto(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getUser().getName(),
                doctor.getUser().getEmail(),
                doctor.getCrm(),
                doctor.getSpecialty()
        );
    }
}
