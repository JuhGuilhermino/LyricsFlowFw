package com.example.application1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress", schema = "public")
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "created_at", columnDefinition = "timestamp without time zone DEFAULT now()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp without time zone DEFAULT now()")
    private LocalDateTime updatedAt;

    @Column(name = "total_tasks_completd")
    private Integer totalTasksCompleted;

    @Column(name = "average_task_score")
    private Float averageTaskScore;

    @Column(name = "total_target_words")
    private Integer totalTargetWords;

    @Column(name = "total_flashcards_count")
    private Integer totalFlashcardsCount;

    public UserProgress() {}

    public UserProgress(Long id, User user, LocalDateTime createdAt, LocalDateTime updatedAt, 
                        Integer totalTasksCompleted, Float averageTaskScore, 
                        Integer totalTargetWords, Integer totalFlashcardsCount) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalTasksCompleted = totalTasksCompleted;
        this.averageTaskScore = averageTaskScore;
        this.totalTargetWords = totalTargetWords;
        this.totalFlashcardsCount = totalFlashcardsCount;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getTotalTasksCompleted() { return totalTasksCompleted; }
    public void setTotalTasksCompleted(Integer totalTasksCompleted) { this.totalTasksCompleted = totalTasksCompleted; }

    public Float getAverageTaskScore() { return averageTaskScore; }
    public void setAverageTaskScore(Float averageTaskScore) { this.averageTaskScore = averageTaskScore; }

    public Integer getTotalTargetWords() { return totalTargetWords; }
    public void setTotalTargetWords(Integer totalTargetWords) { this.totalTargetWords = totalTargetWords; }

    public Integer getTotalFlashcardsCount() { return totalFlashcardsCount; }
    public void setTotalFlashcardsCount(Integer totalFlashcardsCount) { this.totalFlashcardsCount = totalFlashcardsCount; }
}
