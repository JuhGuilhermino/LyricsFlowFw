package com.example.lyricsflowfw.core.domain;

import jakarta.persistence.*;

@MappedSuperclass 
public abstract class BaseSong {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;  

    @Column(columnDefinition = "TEXT")
    private String lyrics;  

    public BaseSong() {}

    public BaseSong(Long id, String title, String lyrics) {
        this.id = id;
        this.title = title;
        this.lyrics = lyrics;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
}

