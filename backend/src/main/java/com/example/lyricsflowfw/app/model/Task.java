package com.example.lyricsflowfw.app.model;

import com.example.lyricsflowfw.core.domain.BaseTask;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task extends BaseTask<User, Song> {

    public Task() {
        super();
    }

    // Agora este construtor compilará perfeitamente porque o super existe!
    public Task(Long id, User user, Song song, Float score, String generatedActivity, String answerKey) {
        super(id, user, song, score, generatedActivity, answerKey);
    }
}