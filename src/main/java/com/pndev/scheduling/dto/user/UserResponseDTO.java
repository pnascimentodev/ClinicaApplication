package com.pndev.scheduling.dto.user;

import com.pndev.scheduling.model.User;
import com.pndev.scheduling.model.enums.Role;

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
