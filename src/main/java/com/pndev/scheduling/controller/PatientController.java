package com.pndev.scheduling.controller;

import com.pndev.scheduling.dto.patient.PatientRequestDTO;
import com.pndev.scheduling.dto.patient.PatientResponseDTO;
import com.pndev.scheduling.model.Patient;
import com.pndev.scheduling.service.PatientService;
import com.pndev.scheduling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;

    @Autowired
    public PatientController(PatientService patientService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
    }

    @PostMapping("/patient/register")
    public ResponseEntity<PatientResponseDTO> registerPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientService.registerPatient(patientRequestDTO);
        return ResponseEntity.ok(PatientResponseDTO.toDto(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok(PatientResponseDTO.toDto(patient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        return ResponseEntity.ok(PatientResponseDTO.toDto(patient));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<PatientResponseDTO> getPatientByCpf(@PathVariable String cpf) {
        Patient patient = patientService.findByCpf(cpf);
        return ResponseEntity.ok(PatientResponseDTO.toDto(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
