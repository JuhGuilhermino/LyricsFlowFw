package com.example.application1.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.framework.learning_core.domain.BaseFlashcard;
import com.framework.learning_core.domain.EnumFlashcardAnswerQuality;

@Entity
@Table(name = "flashcards")
public class Flashcard extends BaseFlashcard<User> {

    // Construtor Padrão exigido pelo JPA
    public Flashcard() {
        super();
    }

    // Construtor Completo passando os tipos concretos
    public Flashcard(Long id, User user, String word, Integer interval, LocalDate nextReviewDate, Float easeFactor, EnumFlashcardAnswerQuality lastQuality, LocalDateTime createdAt) {
        super(id, user, word, interval, nextReviewDate, easeFactor, lastQuality, createdAt);
    }

    // Mapeamento das anotações JPA nos métodos herdados

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() { 
        return super.getId(); 
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Override
    public User getUser() { 
        return super.getUser(); 
    }

    @Override
    public String getWord() { 
        return super.getWord(); 
    }

    @Column(name = "review_interval") // Evita a palavra reservada 'interval' no SQL
    @Override
    public Integer getInterval() { 
        return super.getInterval(); 
    }

    @Override
    public LocalDate getNextReviewDate() { 
        return super.getNextReviewDate(); 
    }

    @Override
    public Float getEaseFactor() { 
        return super.getEaseFactor(); 
    }

    @Enumerated(EnumType.STRING)
    @Override
    public EnumFlashcardAnswerQuality getLastQuality() { 
        return super.getLastQuality(); 
    }

    @Override
    public LocalDateTime getCreatedAt() { 
        return super.getCreatedAt(); 
    }
}
