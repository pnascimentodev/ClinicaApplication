package com.pndev.scheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pndev.scheduling.model.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    List<Doctor> findBySpecialty(String specialty);

    Optional<Doctor> findByCrm(String crm);

    boolean existsByCrm(String crm);

}
