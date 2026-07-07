package com.example.lyricsflowfw.core.domain;

public class LearningProfile {
    private String proficiencyTarget; // ex: "Phrasal Verbs", "Past Tense"
    private String focusArea;          // ex: "Vocabulary", "Grammar", "Syntax"
    private String CEFRLevel;          // ex: "B2", "C1"
    
    // Construtor Padrão (Necessário para frameworks como o Spring/Jackson)
    public LearningProfile() {
    }

    // Construtor Completo
    public LearningProfile(String proficiencyTarget, String focusArea, String CEFRLevel) {
        this.proficiencyTarget = proficiencyTarget;
        this.focusArea = focusArea;
        this.CEFRLevel = CEFRLevel;
    }

    // Getters e Setters
    public String getProficiencyTarget() {return proficiencyTarget;}
    public void setProficiencyTarget(String proficiencyTarget) {this.proficiencyTarget = proficiencyTarget;}

    public String getFocusArea() {return focusArea;}
    public void setFocusArea(String focusArea) {this.focusArea = focusArea;}

    public String getCEFRLevel() {return CEFRLevel;}
    public void setCEFRLevel(String CEFRLevel) {this.CEFRLevel = CEFRLevel;}
}
