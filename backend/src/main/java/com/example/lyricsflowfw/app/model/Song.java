package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseSong;
import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song extends BaseSong {

    // PONTO FLEXÍVEL: Especificidade declarada apenas nesta aplicação
    private String artist; 

    public Song() {
        super();
    }

    // Construtor Completo utilizando os campos da classe pai via super()
    public Song(Long id, String title, String artist, String lyrics) {
        super(id, title, lyrics);
        this.artist = artist;
    }

    // Getter e Setter do campo variável
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
}
