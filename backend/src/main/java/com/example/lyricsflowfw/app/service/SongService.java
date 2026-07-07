package com.example.lyricsflowfw.app.service;

import com.example.lyricsflowfw.app.dto.MusicResponseDTO;
import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.app.model.User;
import com.example.lyricsflowfw.app.repository.SongRepository;
import com.example.lyricsflowfw.app.repository.UserRepository;
import com.example.lyricsflowfw.core.service.ContentService;
import com.example.lyricsflowfw.core.service.ExternalContentProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService extends ContentService<Song, User> {

    // Construtor injetando os repositórios da aplicação e a lista dinâmica de provedores externos
    public SongService(UserRepository userRepository, 
                       SongRepository songRepository,
                       List<ExternalContentProvider<Song>> contentProviders) {
        super(userRepository, songRepository, contentProviders);
    }

    /**
     * Recupera todas as músicas do banco de dados (reaproveitando a lógica do core)
     * e converte o resultado para a lista de DTOs esperada pela aplicação.
     */
    public List<MusicResponseDTO> listAllSongs() {
        // Invoca o método fixo herdado da superclasse ContentService
        List<Song> songs = super.findAllContentEntities();

        return songs.stream().map(song -> {
            MusicResponseDTO dto = new MusicResponseDTO();
            dto.setId(song.getId());
            dto.setTitle(song.getTitle());
            dto.setArtist(song.getArtist()); // Atributo do ponto flexível mapeado
            dto.setLyrics(song.getLyrics());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * PONTO FLEXÍVEL: Orquestra a busca de música em provedores externos (ex: Genius).
     * Se encontrado, converte e retorna o DTO pronto para a aplicação.
     */
    public Optional<MusicResponseDTO> fetchAndConvertExternalSong(String title, String artist) {
        // Dispara o resolvedor de provedores do core passando "API_GENIUS" como gatilho do sourceType
        return super.searchExternalContent(title, "API_GENIUS", artist)
                .map(song -> {
                    MusicResponseDTO dto = new MusicResponseDTO();
                    dto.setId(song.getId());
                    dto.setTitle(song.getTitle());
                    dto.setArtist(song.getArtist());
                    dto.setLyrics(song.getLyrics());
                    return dto;
                });
    }
}