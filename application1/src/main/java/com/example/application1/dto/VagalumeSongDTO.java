package com.example.application1.dto;

public class VagalumeSongDTO {
    private String id;
    private String title;
    private String lyrics;

    public VagalumeSongDTO() {}

    public VagalumeSongDTO(String id, String title, String lyrics) {
        this.id = id;
        this.title = title;
        this.lyrics = lyrics;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
}
