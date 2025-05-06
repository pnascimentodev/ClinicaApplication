package com.pndev.ClinicaApplication.dto.user;

import com.pndev.ClinicaApplication.model.enums.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Role role
) {}
