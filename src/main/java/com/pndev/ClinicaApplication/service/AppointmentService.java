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
        if(appointmentRepository.existsByDoctorIdAndDateTime(dto.getDoctorId(), dto.getDateTime())) {
            throw new DomainException(
                    DomainException.ErrorType.APPOINTMENT_CONFLICT,
                    "Appointment conflict: Doctor is already booked at this time."
            );
        }
        if (appointmentRepository.existsByPatientIdAndDateTime(dto.getPatientId(), dto.getDateTime())) {
            throw new DomainException(
                    DomainException.ErrorType.APPOINTMENT_CONFLICT,
                    "Appointment conflict: Patient already has an appointment at this time."
            );
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDateTime(dto.getDateTime());
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
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        if (appointments.isEmpty()) {
            throw new DomainException(
                    DomainException.ErrorType.APPOINTMENT_NOT_FOUND,
                    "No appointments found for doctor with id: " + doctorId
            );
        }
        return appointments;
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        if (appointments.isEmpty()) {
            throw new DomainException(
                    DomainException.ErrorType.APPOINTMENT_NOT_FOUND,
                    "No appointments found for patient with id: " + patientId
            );
        }
        return appointments;
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

    public Appointment doctorReschedule(Long appointmentId, AppointmentRequestDTO dto) {
        Appointment appointment = getAppointmentById(appointmentId);

        if (!appointment.getDoctor().getId().equals(dto.getDoctorId()) ||
            !appointment.getPatient().getId().equals(dto.getPatientId())){
            throw new DomainException(
                    DomainException.ErrorType.UNAUTHORIZED_ACTION,
                    "You are not authorized to reschedule this appointment."
            );
        }
        if (appointmentRepository.existsByDoctorIdAndDateTime(dto.getDoctorId(), dto.getDateTime()) ||
                appointmentRepository.existsByPatientIdAndDateTime(dto.getPatientId(), dto.getDateTime())) {
            throw new DomainException(
                    DomainException.ErrorType.APPOINTMENT_CONFLICT,
                    "New date/time is not available."
            );
        }

        appointment.setDateTime(dto.getDateTime());
        return appointmentRepository.save(appointment);
    }

    public Appointment patientReschedule(Long appointmentId, AppointmentRequestDTO dto) {
        Appointment appointment = getAppointmentById(appointmentId);

        Doctor newDoctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new DomainException(
                        DomainException.ErrorType.DOCTOR_NOT_FOUND,
                        "Doctor not found with id: " + dto.getDoctorId()
                ));

        Patient patient = appointment.getPatient();

        if (!patient.getId().equals(dto.getPatientId())) {
            throw new DomainException(
                    DomainException.ErrorType.UNAUTHORIZED_ACTION,
                    "You are not authorized to reschedule this appointment."
            );
        }

        if (appointmentRepository.existsByDoctorIdAndDateTime(dto.getDoctorId(), dto.getDateTime()) ||
        appointmentRepository.existsByPatientIdAndDateTime(dto.getPatientId(), dto.getDateTime())) {
            throw new DomainException(
                    DomainException.ErrorType.APPOINTMENT_CONFLICT,
                    "New date/time is not available."
            );
        }
        appointment.setDoctor(newDoctor);
        appointment.setDateTime(dto.getDateTime());
        return appointmentRepository.save(appointment);
    }

    public void patientCancelAppointment(Long appointmentId, Long patientId) {
        Appointment appointment = getAppointmentById(appointmentId);

        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new DomainException(
                    DomainException.ErrorType.UNAUTHORIZED_ACTION,
                    "You are not authorized to cancel this appointment."
            );
        }
        appointmentRepository.delete(appointment);
    }

}
