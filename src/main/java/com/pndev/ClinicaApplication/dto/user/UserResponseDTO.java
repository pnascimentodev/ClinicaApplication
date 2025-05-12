package com.pndev.ClinicaApplication.dto.user;

import com.pndev.ClinicaApplication.model.User;
import com.pndev.ClinicaApplication.model.enums.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Role role
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
