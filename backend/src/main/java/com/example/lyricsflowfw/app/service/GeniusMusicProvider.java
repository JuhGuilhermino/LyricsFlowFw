package com.example.lyricsflowfw.app.service;

import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.core.service.ExternalContentProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Component
public class GeniusMusicProvider implements ExternalContentProvider<Song> {

    private final RestClient restClient;

    @Value("${genius.api.token}")
    private String apiToken;

    public GeniusMusicProvider() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.genius.com")
                .build();
    }

    @Override
    public Optional<Song> fetchExternalContent(String sourceIdentifier, String sourceType, String... extraParams) {
        // Mapeamento lógico: sourceIdentifier é o title, extraParams[0] é o artist
        String title = sourceIdentifier;
        String artist = (extraParams.length > 0) ? extraParams[0] : "";
        String searchQuery = artist.isEmpty() ? title : artist + " " + title;

        System.out.println("[GeniusProvider] Iniciando busca para: " + searchQuery);

        try {
            // 1. Chamar a API do Genius
            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/search").queryParam("q", searchQuery).build())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiToken)
                    .retrieve()
                    .body(Map.class);

            // 2. Extrair a URL
            String songUrl = extractUrlFromResponse(response);
            System.out.println("[GeniusProvider] URL encontrada no Genius: " + songUrl);

            if (songUrl == null) {
                System.out.println("[GeniusProvider] Falha: Nenhuma URL de música foi retornada pela API.");
                return Optional.empty();
            }

            // 3. Fazer o Scraping com os seletores modernos do Genius
            String lyrics = scrapeLyrics(songUrl);
            System.out.println("[GeniusProvider] Tamanho da letra capturada: " + lyrics.length() + " caracteres.");

            if (lyrics.isEmpty()) {
                System.out.println("[GeniusProvider] Falha: O parser HTML não conseguiu extrair texto do link.");
                return Optional.empty();
            }

            // Instancia a classe local passando title pego do sourceIdentifier
            return Optional.of(new Song(null, title, artist, lyrics));

        } catch (Exception e) {
            System.err.println("[GeniusProvider] ERRO CRÍTICO NA INTEGRAÇÃO:");
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean supportsSource(String sourceType) {
        // Diz explicitamente ao Core que este provedor lida com o tipo de fonte "API_GENIUS"
        return "API_GENIUS".equalsIgnoreCase(sourceType);
    }

    private String extractUrlFromResponse(Map<String, Object> response) {
        try {
            var responseMap = (Map<String, Object>) response.get("response");
            var hits = (List<Map<String, Object>>) responseMap.get("hits");
            if (hits == null || hits.isEmpty()) return null;

            var firstHit = hits.get(0);
            var result = (Map<String, Object>) firstHit.get("result");
            return (String) result.get("url");
        } catch (Exception e) {
            return null;
        }
    }

    private String scrapeLyrics(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .timeout(5000)
                    .get();

            Elements lyricsElements = doc.select("div[class^=LyricsPage__LyricsContainer], div[data-lyrics-container=true], .lyrics");
            
            if (lyricsElements.isEmpty()) {
                return "";
            }

            doc.select("br").append("\\n");
            
            StringBuilder fullLyrics = new StringBuilder();
            lyricsElements.forEach(element -> fullLyrics.append(element.text()).append("\n"));

            String cleanLyrics = fullLyrics.toString().replace("\\n", "\n").trim();

            int indexRealStart = -1;
            if (cleanLyrics.contains("[Verse")) {
                indexRealStart = cleanLyrics.indexOf("[Verse");
            } else if (cleanLyrics.contains("[Chorus")) {
                indexRealStart = cleanLyrics.indexOf("[Chorus");
            } else if (cleanLyrics.contains("[Intro")) {
                indexRealStart = cleanLyrics.indexOf("[Intro");
            }

            if (indexRealStart != -1) {
                cleanLyrics = cleanLyrics.substring(indexRealStart);
            } else {
                cleanLyrics = cleanLyrics.replaceAll("(?i).*?\\bLyrics\\b", "").trim();
            }

            return cleanLyrics;
        } catch (Exception e) {
            System.err.println("[GeniusProvider] Falha ao raspar HTML da página: " + e.getMessage());
            return "";
        }
    }
}
