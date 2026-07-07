package com.example.lyricsflowfw.core.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
public abstract class BaseTask<U extends BaseUser, C extends BaseContent> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = BaseUser.class)
    @JoinColumn(name = "user_id")
    private U user;

    @ManyToOne(targetEntity = BaseContent.class)
    @JoinColumn(name = "content_id") // Alterado de song_id para content_id
    private C content; // Alterado de song para content

    @Column(name = "score")
    private Float score;

    @Column(name = "answer_key", columnDefinition = "TEXT") // Alterado de masked_lyrics para answer_key
    private String answerKey; // Alterado de maskedLyrics para answerKey

    @ElementCollection
    @CollectionTable(name = "task_target_words", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "target_word")
    private List<String> targetWords;

    private LocalDateTime completedAt;

    public BaseTask() {}

    public BaseTask(Long id, U user, C content, Float score, String answerKey, List<String> targetWords, LocalDateTime completedAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.score = score;
        this.answerKey = answerKey;
        this.targetWords = targetWords;
        this.completedAt = completedAt;
    }

    // Getters e Setters Atualizados
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public U getUser() { return user; }
    public void setUser(U user) { this.user = user; }

    public C getContent() { return content; }
    public void setContent(C content) { this.content = content; }

    public Float getScore() { return score; }
    public void setScore(Float score) { this.score = score; }

    public String getAnswerKey() { return answerKey; }
    public void setAnswerKey(String answerKey) { this.answerKey = answerKey; }

    public List<String> getTargetWords() { return targetWords; }
    public void setTargetWords(List<String> targetWords) { this.targetWords = targetWords; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
