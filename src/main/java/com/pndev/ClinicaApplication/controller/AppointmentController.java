package com.pndev.ClinicaApplication.controller;

import com.pndev.ClinicaApplication.dto.appointment.AppointmentRequestDTO;
import com.pndev.ClinicaApplication.dto.appointment.AppointmentResponseDTO;
import com.pndev.ClinicaApplication.model.Appointment;
import com.pndev.ClinicaApplication.model.Patient;
import com.pndev.ClinicaApplication.model.enums.AppointmentStatus;
import com.pndev.ClinicaApplication.service.AppointmentService;
import com.pndev.ClinicaApplication.service.DoctorService;
import com.pndev.ClinicaApplication.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> registerAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = appointmentService.registerAppointment(appointmentRequestDTO);
        return ResponseEntity.ok(AppointmentResponseDTO.toDto(appointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(AppointmentResponseDTO.toDto(appointment));
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatientId(Long patientId) {
        Patient patient = patientService.findById(patientId);
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(AppointmentResponseDTO.toDtoList(appointments));
    }
    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByDoctorId(Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(AppointmentResponseDTO.toDtoList(appointments));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponseDTO> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam AppointmentStatus status
    ) {
        Appointment appointment = appointmentService.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok(AppointmentResponseDTO.toDto(appointment));
    }

    @PutMapping("/{id}/reschedule/doctor")
    public ResponseEntity<AppointmentResponseDTO> doctorReschedule(
            @PathVariable("id") Long appointmentId,
            @RequestParam Long doctorId,
            @RequestBody AppointmentRequestDTO appointmentRequestDTO
    ) {
        Appointment appointment = appointmentService.doctorReschedule(appointmentId, doctorId, appointmentRequestDTO);
        return ResponseEntity.ok(AppointmentResponseDTO.toDto(appointment));
    }


    @PutMapping("/{id}/reschedule/patient")
    public ResponseEntity<AppointmentResponseDTO> patientReschedule(
            @PathVariable("id") Long appointmentId,
            @RequestParam Long patientId,
            @RequestBody AppointmentRequestDTO appointmentRequestDTO
    ) {
        Appointment appointment = appointmentService.patientReschedule(appointmentId, patientId, appointmentRequestDTO);
        return ResponseEntity.ok(AppointmentResponseDTO.toDto(appointment));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable("id") Long appointmentId,
            @RequestParam Long patientId
    ) {
        appointmentService.patientCancelAppointment(appointmentId, patientId);
        return ResponseEntity.noContent().build();
    }

}
