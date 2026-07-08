package com.medilabo.front.controller;

import com.medilabo.front.model.Front;
import com.medilabo.front.model.Note;
import com.medilabo.front.service.AssessmentService;
import com.medilabo.front.service.FrontService;
import com.medilabo.front.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class FrontController {

    private final FrontService frontService;
    private final NoteService noteService;
    private final AssessmentService assessmentService;

    public FrontController(
            FrontService frontService,
            NoteService noteService,
            AssessmentService assessmentService) {
        this.frontService = frontService;
        this.noteService = noteService;
        this.assessmentService = assessmentService;
    }

    @GetMapping("/patients")
    public String getPatients(Model model) {
        model.addAttribute("patients", frontService.getAllPatients());
        return "patients";
    }

    @GetMapping("/patients/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Front());
        return "add-patient";
    }

    @PostMapping("/patients/add")
    public String addPatient(
            @Valid @ModelAttribute("patient") Front patient,
            BindingResult result) {

        if (result.hasErrors()) {
            return "add-patient";
        }

        frontService.addPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Front patient = frontService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "update-patient";
    }

    @PostMapping("/patients/update/{id}")
    public String updatePatient(
            @PathVariable Long id,
            @Valid @ModelAttribute("patient") Front patient,
            BindingResult result) {

        if (result.hasErrors()) {
            return "update-patient";
        }

        frontService.updatePatient(id, patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/{id}/notes")
    public String showPatientNotes(@PathVariable Long id, Model model) {
        Front patient = frontService.getPatientById(id);

        Note newNote = new Note();
        newNote.setPatientId(id);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", noteService.getNotesByPatientId(id));
        model.addAttribute("note", newNote);
        model.addAttribute("assessment", assessmentService.getPatientRisk(id));

        return "patient-notes";
    }

    @PostMapping("/patients/{id}/notes")
    public String addPatientNote(
            @PathVariable Long id,
            @ModelAttribute("note") Note note) {

        note.setId(null);
        note.setPatientId(id);
        noteService.addNote(note);

        return "redirect:/patients/" + id + "/notes";
    }
}