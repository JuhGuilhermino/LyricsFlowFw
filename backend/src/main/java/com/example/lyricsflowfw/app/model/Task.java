package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseTask;
import com.example.lyricsflowfw.app.model.Song;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task extends BaseTask<User, Song> {

    public Task() {
        super();
    }

    // Construtor atualizado repassando os parâmetros ajustados para o super
    public Task(Long id, User user, Song song, Float score, String answerKey, List<String> targetWords, LocalDateTime completedAt) {
        super(id, user, song, score, answerKey, targetWords, completedAt);
    }
}