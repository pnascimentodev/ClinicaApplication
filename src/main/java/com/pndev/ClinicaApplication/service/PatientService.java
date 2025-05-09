package com.pndev.ClinicaApplication.service;

import com.pndev.ClinicaApplication.dto.patient.PatientRequestDTO;
import com.pndev.ClinicaApplication.exceptions.DomainException;
import com.pndev.ClinicaApplication.model.Patient;
import com.pndev.ClinicaApplication.model.User;
import com.pndev.ClinicaApplication.repository.PatientRepository;
import com.pndev.ClinicaApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public PatientService(PatientRepository patientRepository, UserService userService, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Patient registerPatient(PatientRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.USER_NOT_FOUND,
                        "User not found with id: " + dto.getUserId()
                ));

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setCpf(dto.getCpf());
        patient.setPhone(dto.getPhone());

        return patientRepository.save(patient);
    }
}
