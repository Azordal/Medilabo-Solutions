package com.medilabo.patient.controller;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public Patient addPatient(@Valid @RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody Patient patient) {

        return patientService.updatePatient(id, patient);
    }
}