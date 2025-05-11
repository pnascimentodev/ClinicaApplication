package com.pndev.ClinicaApplication.service;

import com.pndev.ClinicaApplication.dto.appointment.AppointmentRequestDTO;
import com.pndev.ClinicaApplication.exceptions.DomainException;
import com.pndev.ClinicaApplication.model.Appointment;
import com.pndev.ClinicaApplication.model.Doctor;
import com.pndev.ClinicaApplication.model.Patient;
import com.pndev.ClinicaApplication.model.enums.AppointmentStatus;
import com.pndev.ClinicaApplication.repository.AppointmentRepository;
import com.pndev.ClinicaApplication.repository.DoctorRepository;
import com.pndev.ClinicaApplication.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment registerAppointment(AppointmentRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.DOCTOR_NOT_FOUND,
                        "Doctor not found with id: " + dto.getDoctorId()
                ));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.PATIENT_NOT_FOUND,
                        "Patient not found with id: " + dto.getPatientId()
                ));

        Appointment appointment = new Appointment();
        appointment.setDateTime(dto.getDateTime());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.APPOINTMENT_NOT_FOUND,
                        "Appointment not found with id: " + appointmentId
                ));
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.APPOINTMENT_NOT_FOUND,
                        "Appointment not found with id: " + appointmentId
                ));
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
    public Appointment updateAppointmentDateTime(Long appointmentId, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.APPOINTMENT_NOT_FOUND,
                        "Appointment not found with id: " + appointmentId
                ));
        appointment.setDateTime(dto.getDateTime());
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.APPOINTMENT_NOT_FOUND,
                        "Appointment not found with id: " + appointmentId
                ));
        appointmentRepository.delete(appointment);
    }
}
