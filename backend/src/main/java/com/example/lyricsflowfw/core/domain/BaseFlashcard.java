package com.example.lyricsflowfw.core.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.example.lyricsflowfw.app.enums.FlashcardAnswerQuality; 

@MappedSuperclass
public abstract class BaseFlashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    @Column(name = "review_interval")
    private Integer interval;

    private LocalDate nextReviewDate;

    private Float easeFactor;

    @Enumerated(EnumType.STRING)
    private FlashcardAnswerQuality lastQuality; 

    private LocalDateTime createdAt;

    public BaseFlashcard() {}

    public BaseFlashcard(Long id, String word, Integer interval, LocalDate nextReviewDate, Float easeFactor, FlashcardAnswerQuality lastQuality, LocalDateTime createdAt) {
        this.id = id;
        this.word = word;
        this.interval = interval;
        this.nextReviewDate = nextReviewDate;
        this.easeFactor = easeFactor;
        this.lastQuality = lastQuality;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public Integer getInterval() { return interval; }
    public void setInterval(Integer interval) { this.interval = interval; }

    public LocalDate getNextReviewDate() { return nextReviewDate; }
    public void setNextReviewDate(LocalDate nextReviewDate) { this.nextReviewDate = nextReviewDate; }

    public Float getEaseFactor() { return easeFactor; }
    public void setEaseFactor(Float easeFactor) { this.easeFactor = easeFactor; }

    public FlashcardAnswerQuality getLastQuality() { return lastQuality; }
    public void setLastQuality(FlashcardAnswerQuality lastQuality) { this.lastQuality = lastQuality; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}