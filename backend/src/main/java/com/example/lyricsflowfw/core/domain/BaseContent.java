package com.example.lyricsflowfw.core.domain;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public BaseContent() {}
    public BaseContent(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
