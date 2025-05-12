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

    public Patient registerPatient(PatientRequestDTO patientRequestDTO) {
        User user = userService.findById(patientRequestDTO.getUserId());
        if (patientRepository.existsByCpf(patientRequestDTO.getCpf())) {
            throw new DomainException(
                    DomainException.ErrorType.PATIENT_ALREADY_EXISTS,
                    "Patient already exists with CPF: " + patientRequestDTO.getCpf()
            );
        }

        Patient patient = new Patient();
        patient.setCpf(patientRequestDTO.getCpf());
        patient.setPhone(patientRequestDTO.getPhone());
        patient.setUser(user);

        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        Patient patient = findById(id);
        patient.setCpf(patientRequestDTO.getCpf());
        patient.setPhone(patientRequestDTO.getPhone());
        return patientRepository.save(patient);
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.PATIENT_NOT_FOUND,
                        "Patient not found with id: " + id
                ));
    }

    public Patient findByCpf(String cpf) {
        return patientRepository.findByCpf(cpf)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.PATIENT_NOT_FOUND,
                        "Patient not found with CPF: " + cpf
                ));
    }

    public void deletePatient(Long id) {
        Patient patient = findById(id);
        User user = patient.getUser();

        patientRepository.delete(patient);
        userRepository.delete(user);
    }
}
