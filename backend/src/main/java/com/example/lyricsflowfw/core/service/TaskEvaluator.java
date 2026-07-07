package com.example.lyricsflowfw.core.service;

public interface TaskEvaluator {
    // Avalia a resposta do usuário comparando com o gabarito sob regras customizadas
    float evaluate(String userResponse, String answerKey, java.util.List<String> targets);
}
