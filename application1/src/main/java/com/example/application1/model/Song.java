package com.example.application1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String artist;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    // Construtor Padrão
    public Song() {}

    // Construtor Completo
    public Song(Long id, String title, String artist, String lyrics) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }

}
