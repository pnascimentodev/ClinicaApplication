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

    public void updateName(Long id, String name) {
        Patient patient = findById(id);
        patient.getUser().setName(name);
        userRepository.save(patient.getUser());
    }

    public void updatePatientPhone(Long id, String phone) {
        Patient patient = findById(id);
        patient.setPhone(phone);
        patientRepository.save(patient);
    }
    public void deletePatient(Long id) {
        Patient patient = findById(id);
        patientRepository.delete(patient);
    }
}
