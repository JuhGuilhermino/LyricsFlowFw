package com.example.lyricsflowfw.core.service;

import com.example.lyricsflowfw.core.domain.BaseContent;
import com.example.lyricsflowfw.core.domain.LearningProfile;
import com.example.lyricsflowfw.core.dto.AiTaskResponseDTO;

public interface AiTaskGeneratorStrategy<C extends BaseContent> {
    
    // Suporta qualquer tipo de conteúdo (Música, Texto Livre, Artigo)
    AiTaskResponseDTO generateTask(C content, LearningProfile profile);
    
    // Define qual tipo de atividade essa estratégia gera (ex: "GAP_FILLING", "QUIZ")
    String getSupportedTaskType();
}
