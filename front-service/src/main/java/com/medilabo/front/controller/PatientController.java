package com.medilabo.front.controller;

import com.medilabo.front.model.Patient;
import com.medilabo.front.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/patients/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "add-patient";
    }

    @PostMapping("/patients/add")
    public String addPatient(
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult result) {

        if (result.hasErrors()) {
            return "add-patient";
        }

        patientService.addPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "update-patient";
    }

    @PostMapping("/patients/update/{id}")
    public String updatePatient(
            @PathVariable Long id,
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult result) {

        if (result.hasErrors()) {
            return "update-patient";
        }

        patientService.updatePatient(id, patient);
        return "redirect:/patients";
    }
}