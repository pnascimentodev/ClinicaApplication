package com.pndev.scheduling.service;


import com.pndev.scheduling.dto.doctor.DoctorRequestDTO;
import com.pndev.scheduling.exceptions.DomainException;
import com.pndev.scheduling.model.Doctor;
import com.pndev.scheduling.model.User;
import com.pndev.scheduling.repository.DoctorRepository;
import com.pndev.scheduling.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    public DoctorService(DoctorRepository doctorRepository, UserService userService, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.userRepository = userRepository;

    }

    public Doctor registerDoctor(DoctorRequestDTO doctorRequestDTO) {
        User user = userService.findById(doctorRequestDTO.getUserId());
        if (doctorRepository.existsByCrm(doctorRequestDTO.getCrm())) {
            throw new DomainException(
                    DomainException.ErrorType.DOCTOR_ALREADY_EXISTS,
                    "Doctor already exists with CRM: " + doctorRequestDTO.getCrm()
            );
        }

        Doctor doctor = new Doctor();
        doctor.setCrm(doctorRequestDTO.getCrm());
        doctor.setSpecialty(doctorRequestDTO.getSpecialty());
        doctor.setUser(user);

        return doctorRepository.save(doctor);
    }

    public  Doctor findById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.DOCTOR_NOT_FOUND,
                        "Doctor not found with ID: " + id
                ));
    }

    public Doctor update(Long id, DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = findById(id);
        doctor.setCrm(doctorRequestDTO.getCrm());
        doctor.setSpecialty(doctorRequestDTO.getSpecialty());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = findById(id);
        User user = doctor.getUser();
        doctorRepository.delete(doctor);
        userRepository.delete(user);
    }
}
