package com.medilabo.assessment.controller;

import com.medilabo.assessment.model.AssessmentResult;
import com.medilabo.assessment.model.Note;
import com.medilabo.assessment.model.Patient;
import com.medilabo.assessment.service.AssessmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/patient/{patientId}")
    public Patient getPatient(@PathVariable Long patientId) {
        return assessmentService.getPatient(patientId);
    }

    @GetMapping("/patient/{patientId}/notes")
    public List<Note> getPatientNotes(@PathVariable Long patientId) {
        return assessmentService.getPatientNotes(patientId);
    }

    @GetMapping("/patient/{patientId}/risk")
    public AssessmentResult assessPatientRisk(@PathVariable Long patientId) {
        return assessmentService.assessPatientRisk(patientId);
    }
}