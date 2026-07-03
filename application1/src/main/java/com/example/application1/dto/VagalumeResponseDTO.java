package com.example.application1.dto;

import java.util.List;

public class VagalumeResponseDTO {
    private String type;
    private List<VagalumeSongDTO> mus;

    // Construtor Padrão
    public VagalumeResponseDTO() {}

    // Construtor Completo
    public VagalumeResponseDTO(String type, List<VagalumeSongDTO> mus) {
        this.type = type;
        this.mus = mus;
    }

    // Getters e Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<VagalumeSongDTO> getMus() { return mus; }
    public void setMus(List<VagalumeSongDTO> mus) { this.mus = mus; }
}
