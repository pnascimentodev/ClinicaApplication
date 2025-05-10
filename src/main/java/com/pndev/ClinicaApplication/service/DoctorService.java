package com.pndev.ClinicaApplication.service;


import com.pndev.ClinicaApplication.dto.doctor.DoctorRequestDTO;
import com.pndev.ClinicaApplication.exceptions.DomainException;
import com.pndev.ClinicaApplication.model.Doctor;
import com.pndev.ClinicaApplication.model.User;
import com.pndev.ClinicaApplication.repository.DoctorRepository;
import com.pndev.ClinicaApplication.repository.UserRepository;
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

    public Doctor registerDoctor(DoctorRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.USER_NOT_FOUND,
                        "User not found with id: " + dto.getUserId()
                ));
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setCrm(dto.getCrm());
        doctor.setSpecialty(dto.getSpecialty());

        return doctorRepository.save(doctor);
    }

    public Doctor findByCrm(String crm) {
        return doctorRepository.findByCrm(crm)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.DOCTOR_NOT_FOUND,
                        "Doctor not found with CRM: " + crm
                ));
    }

    public void updateName(Long id, String name) {
        Doctor doctor = findByCrm(id.toString());
        doctor.getUser().setName(name);
        userRepository.save(doctor.getUser());
    }

    public void updateSpecialty(Long id, String specialty) {
        Doctor doctor = findByCrm(id.toString());
        doctor.setSpecialty(specialty);
        doctorRepository.save(doctor);
    }

    public void updateCrm(Long id, String crm) {
        Doctor doctor = findByCrm(id.toString());
        doctor.setCrm(crm);
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = findByCrm(id.toString());
        doctorRepository.delete(doctor);
    }


}
