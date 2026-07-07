package com.example.lyricsflowfw.app.client;

import com.example.lyricsflowfw.app.dto.FlashcardDetailsResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

@Component
public class GeminiClient {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    public GeminiClient(ChatModel chatModel) {
        this.chatModel = chatModel;

        // Configura o limite de leitura para aceitar os "traços" longos da música sem bugar
        this.objectMapper = new ObjectMapper();
        this.objectMapper.getFactory().setStreamReadConstraints(
            com.fasterxml.jackson.core.StreamReadConstraints.builder()
                .maxNumberLength(10000) // Aumenta o limite para 10 mil caracteres
                .build()
        );
    }

    public FlashcardDetailsResponseDTO generateFlashcardDetails(String word) {
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra fornecida não pode ser nula ou vazia.");
        }

        String prompt = String.format("""
            Gere os detalhes educacionais para a seguinte palavra em inglês: "%s".
            Você deve responder estritamente com um objeto JSON válido, sem formatações markdown (não inclua ```json), contendo exatamente os seguintes campos em português (exceto as frases em inglês):
            {
              "contextPhrase": "Uma frase curta em inglês contendo a palavra, substituindo a palavra por um traço sublinhado longo '_______' para servir de lacuna.",
              "meaning": "O significado/tradução direta da palavra em português.",
              "partOfSpeech": "A classe gramatical da palavra (Ex: Substantivo, Verbo, Adjetivo, Advérbio).",
              "exampleUsage": "Uma outra frase de exemplo completa em inglês utilizando a palavra naturalmente."
            }
            """, word.trim());

        try {
            String responseJson = this.chatModel.call(prompt);

            if (responseJson == null || responseJson.trim().isEmpty()) {
                throw new RuntimeException("Resposta vazia recebida do Gemini.");
            }

            String cleanJson = responseJson
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();
            return objectMapper.readValue(cleanJson, FlashcardDetailsResponseDTO.class);

        } catch (Exception e) {
            System.err.println("Erro ao gerar detalhes do flashcard para a palavra: " + word + ". Detalhes: " + e.getMessage());
            return null;
        }
    }
}
