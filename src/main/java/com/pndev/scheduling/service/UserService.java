package com.pndev.scheduling.service;

import com.pndev.scheduling.dto.user.UserRegisterDTO;
import com.pndev.scheduling.exceptions.DomainException;
import com.pndev.scheduling.model.User;
import com.pndev.scheduling.model.enums.Role;
import com.pndev.scheduling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DomainException(
                    DomainException.ErrorType.USER_ALREADY_EXISTS,
                    "User already exists with email: " + dto.getEmail()
            );
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    public User registerAdmin(String name, String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new DomainException(
                    DomainException.ErrorType.USER_ALREADY_EXISTS,
                    "Admin already exists with email: " + email
            );
        }

        User admin = new User();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(rawPassword));
        admin.setRole(Role.ADMIN);

        return userRepository.save(admin);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.USER_NOT_FOUND,
                        "User not found with email: " + email
                ));
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.USER_NOT_FOUND,
                        "User not found with id: " + id
                ));
    }

    public User update(Long id, String name, String email, String rawPassword, Role role) {
        User user = findById(id);

        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new DomainException(
                    DomainException.ErrorType.USER_ALREADY_EXISTS,
                    "Another user already exists with email: " + email
            );
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public User authenticate(String email, String rawPassword) {
        User user = findByEmail(email);
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new DomainException(
                    DomainException.ErrorType.INVALID_CREDENTIALS,
                    "Invalid credentials for user: " + email
            );
        }
        return user;
    }
}
