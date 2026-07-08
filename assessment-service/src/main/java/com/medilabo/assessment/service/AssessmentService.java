package com.medilabo.assessment.service;

import com.medilabo.assessment.model.AssessmentResult;
import com.medilabo.assessment.model.Note;
import com.medilabo.assessment.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Service
public class AssessmentService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${patient.service.url}")
    private String patientServiceUrl;

    @Value("${note.service.url}")
    private String noteServiceUrl;

    private static final List<String> TRIGGERS = List.of(
            "hemoglobine a1c",
            "microalbumine",
            "taille",
            "poids",
            "fumeur",
            "fumeuse",
            "anormal",
            "cholesterol",
            "vertige",
            "rechute",
            "reaction",
            "anticorps"
    );

    public Patient getPatient(Long patientId) {
        return restTemplate.getForObject(
                patientServiceUrl + "/patients/" + patientId,
                Patient.class
        );
    }

    public List<Note> getPatientNotes(Long patientId) {
        Note[] notes = restTemplate.getForObject(
                noteServiceUrl + "/notes/patient/" + patientId,
                Note[].class
        );

        return Arrays.asList(notes);
    }

    public AssessmentResult assessPatientRisk(Long patientId) {
        Patient patient = getPatient(patientId);
        List<Note> notes = getPatientNotes(patientId);

        int age = calculateAge(patient.getBirthDate());
        int triggerCount = countTriggers(notes);
        String gender = patient.getGender();

        String riskLevel = determineRiskLevel(age, gender, triggerCount);

        return new AssessmentResult(patientId, riskLevel);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private int countTriggers(List<Note> notes) {
        String allNotesContent = notes.stream()
                .map(Note::getContent)
                .filter(content -> content != null)
                .reduce("", (content1, content2) -> content1 + " " + content2);

        String normalizedContent = normalize(allNotesContent);

        int count = 0;

        for (String trigger : TRIGGERS) {
            if (normalizedContent.contains(trigger)) {
                count++;
            }
        }

        return count;
    }

    private String normalize(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        return normalized.toLowerCase();
    }

    private String determineRiskLevel(int age, String gender, int triggerCount) {
        boolean isMale = "M".equalsIgnoreCase(gender);
        boolean isFemale = "F".equalsIgnoreCase(gender);

        if (triggerCount == 0) {
            return "None";
        }

        if (age > 30 && triggerCount >= 2 && triggerCount <= 5) {
            return "Borderline";
        }

        if (age < 30 && isMale && triggerCount >= 5) {
            return "Early onset";
        }

        if (age < 30 && isFemale && triggerCount >= 7) {
            return "Early onset";
        }

        if (age > 30 && triggerCount >= 8) {
            return "Early onset";
        }

        if (age < 30 && isMale && triggerCount >= 3) {
            return "In Danger";
        }

        if (age < 30 && isFemale && triggerCount >= 4) {
            return "In Danger";
        }

        if (age > 30 && triggerCount >= 6) {
            return "In Danger";
        }

        return "None";
    }
}