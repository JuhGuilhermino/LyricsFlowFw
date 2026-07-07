package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseTask;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task extends BaseTask<User, Song> {

    public Task() {
        super();
    }

    // Construtor repassando os tipos concretos da aplicação para o super do framework
    public Task(Long id, User user, Song song, Float score, String maskedLyrics, List<String> targetWords, LocalDateTime completedAt) {
        super(id, user, song, score, maskedLyrics, targetWords, completedAt);
    }
}