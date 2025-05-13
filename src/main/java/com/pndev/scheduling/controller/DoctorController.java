package com.pndev.scheduling.controller;

import com.pndev.scheduling.dto.doctor.DoctorRequestDTO;
import com.pndev.scheduling.dto.doctor.DoctorResponseDTO;
import com.pndev.scheduling.model.Doctor;
import com.pndev.scheduling.service.DoctorService;
import com.pndev.scheduling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final UserService userService;


    @Autowired
    public DoctorController(DoctorService doctorService, UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @PostMapping("/doctor/register")
    public ResponseEntity<DoctorResponseDTO> registerDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorService.registerDoctor(doctorRequestDTO);

        return new ResponseEntity<>(DoctorResponseDTO.toDto(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable Long id, DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorService.update(id, doctorRequestDTO);
        return ResponseEntity.ok(DoctorResponseDTO.toDto(doctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok(DoctorResponseDTO.toDto(doctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
