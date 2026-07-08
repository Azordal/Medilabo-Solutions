package com.medilabo.assessment.model;

public class AssessmentResult {

    private Long patientId;
    private String riskLevel;

    public AssessmentResult() {
    }

    public AssessmentResult(Long patientId, String riskLevel) {
        this.patientId = patientId;
        this.riskLevel = riskLevel;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}