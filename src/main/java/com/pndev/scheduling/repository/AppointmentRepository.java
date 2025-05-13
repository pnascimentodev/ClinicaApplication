package com.pndev.scheduling.repository;

import com.pndev.scheduling.model.Appointment;
import com.pndev.scheduling.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

    boolean existsByPatientIdAndDateTime(Long patientId, LocalDateTime dateTime);
}