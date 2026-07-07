package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseUser;
import com.example.lyricsflowfw.app.enums.LanguageLevel;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends com.example.lyricsflowfw.core.domain.BaseUser {

    @Enumerated(EnumType.STRING)
    private com.example.lyricsflowfw.app.enums.LanguageLevel currentLevel;

    public User() {
        super();
    }

    public com.example.lyricsflowfw.app.enums.LanguageLevel getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(com.example.lyricsflowfw.app.enums.LanguageLevel currentLevel) { this.currentLevel = currentLevel; }
}