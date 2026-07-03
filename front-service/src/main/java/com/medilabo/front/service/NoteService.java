package com.medilabo.front.service;

import com.medilabo.front.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gateway.url}")
    private String gatewayUrl;

    /**
     * Récupère toutes les notes d'un patient.
     */
    public List<Note> getNotesByPatientId(Long patientId) {
        Note[] notes = restTemplate.getForObject(
                gatewayUrl + "/notes/patient/" + patientId,
                Note[].class
        );

        return Arrays.asList(notes);
    }

    /**
     * Ajoute une nouvelle note.
     */
    public void addNote(Note note) {
        restTemplate.postForObject(
                gatewayUrl + "/notes",
                note,
                Note.class
        );
    }
}