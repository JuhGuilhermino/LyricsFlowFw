package com.example.lyricsflowfw.app.service;

import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.core.service.ExternalContentProvider;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class GeniusMusicProvider implements ExternalContentProvider<Song> {

    @Override
    public Optional<Song> fetchExternalContent(String sourceIdentifier, String sourceType, String... extraParams) {
        // No contexto desta API, o identificador principal é o próprio título da música
        String title = sourceIdentifier;
        String artist = (extraParams.length > 0) ? extraParams[0] : "Desconhecido";
        
        // Exemplo fictício de integração externa (Consumindo a API do Genius)
        String mockLyrics = "Letra da música " + title + " do artista " + artist + " (Consumida via API Genius)";
        
        // Retorna a entidade de música preenchida
        return Optional.of(new Song(null, title, artist, mockLyrics));
    }

    @Override
    public boolean supportsSource(String sourceType) {
        // Define que este provedor específico só deve ser acionado para requisições do tipo API_GENIUS
        return "API_GENIUS".equalsIgnoreCase(sourceType);
    }
}
