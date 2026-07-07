package com.example.lyricsflowfw.app.dto;

import com.example.lyricsflowfw.app.model.LearningProfile;

public record TaskGenerationRequestDTO(
    Long userId,
    Long songId,
    LearningProfile profile // Sua classe concreta com o enum LanguageLevel
) {}