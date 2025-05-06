package com.pndev.ClinicaApplication.dto.user;


import com.pndev.ClinicaApplication.model.enums.Role;

public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;
    private Role role;

    public UserRegisterDTO() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
