package com.framework.learning_core.domain;

import java.time.LocalDateTime;

public abstract class BaseUser {
    private Long id;
    private String username;
    private String email;
    private String avatarPath;
    private String currentLevel; // Armazenado como String para manter o framework genérico
    private LocalDateTime createdAt;

    // Construtor Padrão
    public BaseUser() {}

    // Construtor Completo
    public BaseUser(Long id, String username, String email, String avatarPath, String currentLevel, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatarPath = avatarPath;
        this.currentLevel = currentLevel;
        this.createdAt = createdAt;
    }

    // Lógica de negócio comum: validação rudimentar de email
    public boolean hasValidEmail() {
        return this.email != null && this.email.contains("@");
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }

    public String getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(String currentLevel) { this.currentLevel = currentLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
