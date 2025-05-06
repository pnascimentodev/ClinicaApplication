package com.pndev.ClinicaApplication.dto.appointment;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {
    private LocalDateTime dateTime;
    private Long doctorId;
    private Long patientId;

    public AppointmentRequestDTO() {}
    public AppointmentRequestDTO(LocalDateTime dateTime, Long doctorId, Long patientId) {}

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public Long getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    public Long getPatientId() {
        return patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
