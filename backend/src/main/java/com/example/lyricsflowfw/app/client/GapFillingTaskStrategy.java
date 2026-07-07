package com.example.lyricsflowfw.app.client;

import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.app.model.LearningProfile;      // Agora é a implementação concreta da app
import com.example.lyricsflowfw.core.domain.BaseLearningProfile;  // Agora é a interface marcadora do core
import com.example.lyricsflowfw.core.service.AiTaskGeneratorStrategy;
import com.example.lyricsflowfw.core.dto.AiTaskResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GapFillingTaskStrategy implements AiTaskGeneratorStrategy<Song> {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    public GapFillingTaskStrategy(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.getFactory().setStreamReadConstraints(
            com.fasterxml.jackson.core.StreamReadConstraints.builder()
                .maxNumberLength(10000)
                .build()
        );
    }

    @Override
    public AiTaskResponseDTO generateTask(Song content, BaseLearningProfile profile) {
        // Converte a interface genérica do core para o perfil da aplicação
        LearningProfile appProfile = (LearningProfile) profile;

        String lyrics = content.getLyrics();
        
        // Extrai o nível do enum de forma segura
        String currentLevelStr = appProfile.getLanguageLevel() != null ? appProfile.getLanguageLevel().name() : "BEGINNER";

        // REMOVIDOS OS %s DE FOCUS AREA DO PROMPT PARA EVITAR O ERRO DE FORMATO
        String prompt = """
            Você é um professor de inglês especialista no método Cloze Test (exercício de preencher lacunas).
            Sua tarefa é receber a letra de uma música e o nível de proficiência do aluno (%s).
            
            Regras:
            1. Identifique palavras que sejam desafiadoras ou essenciais para o nível %s.
            2. Substitua essas palavras na letra da música pela tag '_'.
            3. Guarde as palavras originais que foram removidas em uma lista ordenada (gabarito).
            4. No máximo 12 lacunas por música.
            5. Não adicione saudações ou textos explicativos, retorne estritamente a estrutura de dados solicitada.
            
            Letra da música de entrada:
            %s

            Você DEVE responder EXCLUSIVAMENTE um objeto JSON com a seguinte estrutura:
            {
            "maskedLyrics": "Letra da música com as lacunas _",
            "targetWords": ["palavra1", "palavra2", "palavra3"]
            }
            """.formatted(currentLevelStr, currentLevelStr, lyrics); // Exatamente 3 argumentos para 3 '%s'

        try {
            String responseJson = this.chatModel.call(prompt);
            
            String cleanJson = responseJson
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            TaskOutputJson output = this.objectMapper.readValue(cleanJson, TaskOutputJson.class);
            String answerKey = String.join(", ", output.targetWords());

            return new AiTaskResponseDTO(output.maskedLyrics(), answerKey, output.targetWords());

        } catch (Exception e) {
            System.err.println("Falha ao gerar Cloze Test via Spring AI: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getSupportedTaskType() {
        return "GAP_FILLING";
    }

    private record TaskOutputJson(String maskedLyrics, List<String> targetWords) {}
}
