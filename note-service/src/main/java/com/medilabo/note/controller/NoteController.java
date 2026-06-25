package com.medilabo.note.controller;

import com.medilabo.note.model.Note;
import com.medilabo.note.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Retourne toutes les notes d'un patient.
     *
     * Exemple :
     * GET /notes/patient/1
     */
    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable Long patientId) {
        return noteService.getNotesByPatientId(patientId);
    }

    /**
     * Ajoute une nouvelle note.
     *
     * Exemple :
     * POST /notes
     */
    @PostMapping
    public Note addNote(@Valid @RequestBody Note note) {
        return noteService.addNote(note);
    }
}