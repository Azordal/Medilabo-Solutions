package com.medilabo.patient.service;

import com.medilabo.patient.model.Patient;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = getPatientById(id);

        if (patient == null) {
            return null;
        }

        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setBirthDate(patientDetails.getBirthDate());
        patient.setGender(patientDetails.getGender());
        patient.setAddress(patientDetails.getAddress());
        patient.setPhone(patientDetails.getPhone());

        return patientRepository.save(patient);
    }
}