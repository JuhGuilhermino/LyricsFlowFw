package com.example.application1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.framework.learning_core.domain.BaseUserProgress;

@Entity
@Table(name = "user_progress", schema = "public")
public class UserProgress extends BaseUserProgress<User> {

    // Construtor Padrão exigido pelo JPA
    public UserProgress() {
        super();
    }

    // Construtor Completo repassando os parâmetros estruturais para o framework
    public UserProgress(Long id, User user, LocalDateTime createdAt, LocalDateTime updatedAt, 
                        Integer totalTasksCompleted, Float averageTaskScore, 
                        Integer totalTargetWords, Integer totalFlashcardsCount) {
        super(id, user, createdAt, updatedAt, totalTasksCompleted, averageTaskScore, totalTargetWords, totalFlashcardsCount);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.setCreatedAt(now);
        this.setUpdatedAt(now);
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }

    // Mapeamento das anotações JPA nos métodos Getter herdados

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() { 
        return super.getId(); 
    }

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @Override
    public User getUser() { 
        return super.getUser(); 
    }

    @Column(name = "created_at", columnDefinition = "timestamp without time zone DEFAULT now()")
    @Override
    public LocalDateTime getCreatedAt() { 
        return super.getCreatedAt(); 
    }

    @Column(name = "updated_at", columnDefinition = "timestamp without time zone DEFAULT now()")
    @Override
    public LocalDateTime getUpdatedAt() { 
        return super.getUpdatedAt(); 
    }

    @Column(name = "total_tasks_completd") // Mantido o typo original da tabela ('completd')
    @Override
    public Integer getTotalTasksCompleted() { 
        return super.getTotalTasksCompleted(); 
    }

    @Column(name = "average_task_score")
    @Override
    public Float getAverageTaskScore() { 
        return super.getAverageTaskScore(); 
    }

    @Column(name = "total_target_words")
    @Override
    public Integer getTotalTargetWords() { 
        return super.getTotalTargetWords(); 
    }

    @Column(name = "total_flashcards_count")
    @Override
    public Integer getTotalFlashcardsCount() { 
        return super.getTotalFlashcardsCount(); 
    }
}
