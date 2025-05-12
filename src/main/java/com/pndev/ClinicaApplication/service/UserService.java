package com.pndev.ClinicaApplication.service;

import com.pndev.ClinicaApplication.dto.user.UserRegisterDTO;
import com.pndev.ClinicaApplication.exceptions.DomainException;
import com.pndev.ClinicaApplication.model.User;
import com.pndev.ClinicaApplication.repository.DoctorRepository;
import com.pndev.ClinicaApplication.repository.UserRepository;
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

    public User register(UserRegisterDTO dto) {
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

    public User userUpdate(Long id, UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DomainException(
                    DomainException.ErrorType.USER_ALREADY_EXISTS,
                    "User already exists with email: " + dto.getEmail()
            );
        }
        User user = findById(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
