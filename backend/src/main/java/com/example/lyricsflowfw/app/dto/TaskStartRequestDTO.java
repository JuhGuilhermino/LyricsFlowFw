package com.example.lyricsflowfw.app.dto;

import com.example.lyricsflowfw.app.model.LearningProfile;

public class TaskStartRequestDTO {
    private Long userId;
    private Long songId;
    private LearningProfile profile; // Incluído para mapear o nível (ex: BEGINNER, INTERMEDIATE)

    // Construtores
    public TaskStartRequestDTO() {}

    public TaskStartRequestDTO(Long userId, Long songId, LearningProfile profile) {
        this.userId = userId;
        this.songId = songId;
        this.profile = profile;
    }

    // Getters e Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public LearningProfile getProfile() { return profile; }
    public void setProfile(LearningProfile profile) { this.profile = profile; }
}
