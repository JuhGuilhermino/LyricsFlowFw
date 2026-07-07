package com.example.lyricsflowfw.app.controller;

import com.example.lyricsflowfw.app.dto.MusicRequestDTO;
import com.example.lyricsflowfw.app.dto.MusicResponseDTO;
import com.example.lyricsflowfw.app.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSongs() {
        try {
            List<MusicResponseDTO> songs = this.songService.listAllSongs();
            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao listar as músicas: " + e.getMessage());
        }
    }


    @PostMapping("/search")
    public ResponseEntity<?> findOrSaveMusic(@RequestBody MusicRequestDTO request) {
        try {
            // Ajustado para invocar a nova nomenclatura definida no SongService
            Optional<MusicResponseDTO> responseOpt = 
                this.songService.fetchAndConvertExternalSong(request.getTitle(), request.getArtist());

            if (responseOpt.isPresent()) {
                return ResponseEntity.ok(responseOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Música não encontrada localmente nem nas plataformas externas.");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a requisição da música: " + e.getMessage());
        }
    }
    
}
