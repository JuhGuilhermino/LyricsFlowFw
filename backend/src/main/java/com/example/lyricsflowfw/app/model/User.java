package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseUser;
import com.example.lyricsflowfw.app.enums.LanguageLevel;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Aqui a tabela física do banco de dados é realmente criada
public class User extends BaseUser {

    @Enumerated(EnumType.STRING)
    private LanguageLevel currentLevel; // Ponto Variável adicionado por este app

    public User() {
        super();
    }

    // Construtor completo utilizando os campos da classe pai
    public User(Long id, String username, String email, String password, String avatarPath, LanguageLevel currentLevel, LocalDateTime createdAt) {
        super(id, username, email, password, avatarPath, createdAt);
        this.currentLevel = currentLevel;
    }

    // Getter e Setter apenas do ponto variável
    public LanguageLevel getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(LanguageLevel currentLevel) { this.currentLevel = currentLevel; }
}