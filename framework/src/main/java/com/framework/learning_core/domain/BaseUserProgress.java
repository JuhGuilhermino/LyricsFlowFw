package com.framework.learning_core.domain;

import java.time.LocalDateTime;

public abstract class BaseUserProgress<U extends BaseUser> {
    private Long id;
    private U user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer totalTasksCompleted;
    private Float averageTaskScore;
    private Integer totalTargetWords;
    private Integer totalFlashcardsCount;

    // Construtor Padrão
    public BaseUserProgress() {}

    // Construtor Completo
    public BaseUserProgress(Long id, U user, LocalDateTime createdAt, LocalDateTime updatedAt, 
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

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public U getUser() { return user; }
    public void setUser(U user) { this.user = user; }

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