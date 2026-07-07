package com.example.lyricsflowfw.app.repository;

import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.core.repository.BaseContentRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SongRepository extends BaseContentRepository<Song> {
    
    // Método específico desta aplicação, combinando o ponto fixo (title) com o ponto flexível (artist)
    Optional<Song> findByArtistAndTitle(String artist, String title);
}
