package com.example.application1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "score")
    private Float score;

    @Column(name = "masked_lyrics", columnDefinition = "TEXT")
    private String maskedLyrics;

    @Column(name = "target_words")
    private List<String> targetWords;

    private LocalDateTime completedAt;

    // Construtor Padrão
    public Task() {}

    // Construtor Completo
    public Task(Long id, User user, Song song, Float score, String maskedLyrics, List<String> targetWords, LocalDateTime completedAt) {
        this.id = id;
        this.user = user;
        this.song = song;
        this.score = score;
        this.maskedLyrics = maskedLyrics;
        this.targetWords = targetWords;
        this.completedAt = completedAt;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }

    public Float getScore() { return score; }
    public void setScore(Float score) { this.score = score; }

    public String getMaskedLyrics() { return maskedLyrics; }
    public void setMaskedLyrics(String maskedLyrics) { this.maskedLyrics = maskedLyrics; }

    public List<String> getTargetWords() { return targetWords; }
    public void setTargetWords(List<String> targetWords) { this.targetWords = targetWords; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}