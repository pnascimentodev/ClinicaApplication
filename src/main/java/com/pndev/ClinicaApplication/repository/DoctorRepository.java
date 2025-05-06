package com.pndev.ClinicaApplication.repository;

import com.pndev.ClinicaApplication.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    List<Doctor> findBySpecialty(String specialty);

    Optional<Doctor> findByCrm(String crm);

    boolean existsByCrm(String crm);

}
