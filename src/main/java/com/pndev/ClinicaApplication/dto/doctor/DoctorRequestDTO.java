package com.pndev.ClinicaApplication.dto.doctor;

public class DoctorRequestDTO {

    private String crm;
    private String specialty;
    private Long userId;

    public DoctorRequestDTO() {}

    public String getCrm() {
        return crm;
    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
