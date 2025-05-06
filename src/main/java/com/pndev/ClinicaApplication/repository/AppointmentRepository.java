package com.pndev.ClinicaApplication.repository;

import com.pndev.ClinicaApplication.model.Appointment;
import com.pndev.ClinicaApplication.model.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentRepository {
    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

}
