package com.medilabo.note.service;

import com.medilabo.note.model.Note;
import com.medilabo.note.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Retourne toutes les notes d'un patient.
     */
    public List<Note> getNotesByPatientId(Long patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    /**
     * Ajoute une nouvelle note.
     */
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }
}