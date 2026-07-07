package com.example.lyricsflowfw.core.dto;

import java.util.List;

public record AiTaskResponseDTO(
    String generatedActivityBody, // O texto modificado pela IA (com lacunas ou perguntas)
    String answerKey,             // O gabarito gerado pela IA
    List<String> targetWords       // Elementos cruciais avaliados (palavras ocultas, termos-chave)
) {}
