package com.example.lyricsflowfw.app.model;

import jakarta.persistence.*;
import com.example.lyricsflowfw.core.domain.BaseFlashcard;
import com.example.lyricsflowfw.app.enums.FlashcardAnswerQuality;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "flashcards")
public class Flashcard extends BaseFlashcard {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Construtor Padrão
    public Flashcard() {
        super();
    }

    // Construtor Completo
    public Flashcard(Long id, User user, String word, Integer interval, LocalDate nextReviewDate, 
                     Float easeFactor, FlashcardAnswerQuality lastQuality, LocalDateTime createdAt) {
        super(id, word, interval, nextReviewDate, easeFactor, lastQuality, createdAt);
        this.user = user;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
