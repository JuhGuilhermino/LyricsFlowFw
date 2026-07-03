package com.framework.learning_core.domain;

import com.framework.learning_core.domain.EnumFlashcardAnswerQuality;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class BaseFlashcard<U extends BaseUser> {
    private Long id;
    private U user;
    private String word;
    private Integer interval;
    private LocalDate nextReviewDate;
    private Float easeFactor;
    private EnumFlashcardAnswerQuality lastQuality; // Enum importado internamente no framework
    private LocalDateTime createdAt;

    // Construtor Padrão
    public BaseFlashcard() {}

    // Construtor Completo
    public BaseFlashcard(Long id, U user, String word, Integer interval, LocalDate nextReviewDate, Float easeFactor, EnumFlashcardAnswerQuality lastQuality, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.word = word;
        this.interval = interval;
        this.nextReviewDate = nextReviewDate;
        this.easeFactor = easeFactor;
        this.lastQuality = lastQuality;
        this.createdAt = createdAt;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public U getUser() { return user; }
    public void setUser(U user) { this.user = user; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public Integer getInterval() { return interval; }
    public void setInterval(Integer interval) { this.interval = interval; }

    public LocalDate getNextReviewDate() { return nextReviewDate; }
    public void setNextReviewDate(LocalDate nextReviewDate) { this.nextReviewDate = nextReviewDate; }

    public Float getEaseFactor() { return easeFactor; }
    public void setEaseFactor(Float easeFactor) { this.easeFactor = easeFactor; }

    public EnumFlashcardAnswerQuality getLastQuality() { return lastQuality; }
    public void setLastQuality(EnumFlashcardAnswerQuality lastQuality) { this.lastQuality = lastQuality; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
