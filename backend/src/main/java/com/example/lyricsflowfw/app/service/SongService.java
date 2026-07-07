package com.example.lyricsflowfw.app.service;

import com.example.lyricsflowfw.app.dto.MusicResponseDTO;
import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.app.repository.SongRepository;
import com.example.lyricsflowfw.app.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SongService {
    private final UserRepository userRepository;
    private final SongRepository songRepository;


    public SongService(UserRepository userRepository, 
                       SongRepository songRepository) {
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    
    public List<MusicResponseDTO> listAllSongs() {
        List<Song> songs = this.songRepository.findAll();

        return songs.stream().map(song -> {
            MusicResponseDTO dto = new MusicResponseDTO();
            dto.setId(song.getId());
            dto.setTitle(song.getTitle());
            dto.setArtist(song.getArtist());
            dto.setLyrics(song.getLyrics());
            return dto;
        }).collect(Collectors.toList());
    }

    // PONTO FLEXÍVEL - Criar um método para buscar músicas
}