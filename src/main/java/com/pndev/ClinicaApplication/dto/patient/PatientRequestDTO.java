package com.pndev.ClinicaApplication.dto.patient;

public class PatientRequestDTO {
    private String cpf;
    private String phone;

    public PatientRequestDTO() {}

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
