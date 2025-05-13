package com.pndev.scheduling.dto.patient;

public class PatientRequestDTO {
    private String cpf;
    private String phone;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
