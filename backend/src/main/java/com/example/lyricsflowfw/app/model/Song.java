package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseContent;
import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song extends BaseContent {

    // PONTO FLEXÍVEL: Atributo específico desta aplicação
    private String artist;

    // Atributo mantido na aplicação para contextualizar o conteúdo como música
    @Column(columnDefinition = "TEXT")
    private String lyrics;

    // Construtor Padrão (Necessário para o Hibernate)
    public Song() {
        super();
    }

    // Construtor Completo repassando os pontos fixos para o super() do framework
    public Song(Long id, String title, String artist, String lyrics) {
        super(id, title); // Inicializa id e title na superclasse BaseContent
        this.artist = artist;
        this.lyrics = lyrics;
    }

    // Getters e Setters apenas dos atributos locais da aplicação
    public String getArtist() { 
        return artist; 
    }
    
    public void setArtist(String artist) { 
        this.artist = artist; 
    }

    public String getLyrics() { 
        return lyrics; 
    }
    
    public void setLyrics(String lyrics) { 
        this.lyrics = lyrics; 
    }
}
