package com.pndev.ClinicaApplication.service;

import com.pndev.ClinicaApplication.model.Appointment;
import com.pndev.ClinicaApplication.model.User;
import com.pndev.ClinicaApplication.model.enums.AppointmentStatus;
import com.pndev.ClinicaApplication.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;

    public AppointmentService(AppointmentRepository appointmentRepository, UserService userService) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
    }

    public AppointmentService createAppointment(LocalDateTime dateTime, Long doctorId, Long patientId) {
        User doctor = userService.findById(doctorId);
        return appointmentRepository.save(new Appointment(dateTime, doctor, patientId, AppointmentStatus.PENDING));
    }
}
