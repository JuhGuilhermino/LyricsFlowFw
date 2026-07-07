package com.example.lyricsflowfw.core.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
public abstract class BaseTask<U extends BaseUser, S extends BaseSong> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private U user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private S song;

    @Column(name = "score")
    private Float score;

    @Column(name = "masked_lyrics", columnDefinition = "TEXT")
    private String maskedLyrics;

    @ElementCollection // Garante o mapeamento da lista simples de Strings como colunas secundárias
    @CollectionTable(name = "task_target_words", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "target_word")
    private List<String> targetWords;

    private LocalDateTime completedAt;

    public BaseTask() {}

    public BaseTask(Long id, U user, S song, Float score, String maskedLyrics, List<String> targetWords, LocalDateTime completedAt) {
        this.id = id;
        this.user = user;
        this.song = song;
        this.score = score;
        this.maskedLyrics = maskedLyrics;
        this.targetWords = targetWords;
        this.completedAt = completedAt;
    }

    // Getters e Setters Genéricos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public U getUser() { return user; }
    public void setUser(U user) { this.user = user; }

    public S getSong() { return song; }
    public void setSong(S song) { this.song = song; }

    public Float getScore() { return score; }
    public void setScore(Float score) { this.score = score; }

    public String getMaskedLyrics() { return maskedLyrics; }
    public void setMaskedLyrics(String maskedLyrics) { this.maskedLyrics = maskedLyrics; }

    public List<String> getTargetWords() { return targetWords; }
    public void setTargetWords(List<String> targetWords) { this.targetWords = targetWords; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
