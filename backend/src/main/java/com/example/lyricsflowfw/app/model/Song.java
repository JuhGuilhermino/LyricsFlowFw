package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseSong;
import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song extends com.example.lyricsflowfw.core.domain.BaseSong {
    
    private String artist; // Atributo específico da Aplicação 1

    public Song() {
        super();
    }

    public Song(Long id, String title, String artist, String lyrics) {
        super(id, title, lyrics); // Repassa para os campos herdados do Core
        this.artist = artist;
    }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
}