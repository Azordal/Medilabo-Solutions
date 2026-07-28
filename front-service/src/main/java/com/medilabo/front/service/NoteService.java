package com.medilabo.front.service;

import com.medilabo.front.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class NoteService {

    private final RestTemplate restTemplate;
    private final String gatewayUrl;

    public NoteService(
            RestTemplate restTemplate,
            @Value("${gateway.url}") String gatewayUrl) {

        this.restTemplate = restTemplate;
        this.gatewayUrl = gatewayUrl;
    }

    public List<Note> getNotesByPatientId(Long patientId) {
        Note[] notes = restTemplate.getForObject(
                gatewayUrl + "/notes/patient/" + patientId,
                Note[].class
        );

        if (notes == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(notes);
    }

    public Note addNote(Note note) {
        Note createdNote = restTemplate.postForObject(
                gatewayUrl + "/notes",
                note,
                Note.class
        );

        if (createdNote == null) {
            throw new IllegalStateException(
                    "La note n'a pas pu être créée."
            );
        }

        return createdNote;
    }
}