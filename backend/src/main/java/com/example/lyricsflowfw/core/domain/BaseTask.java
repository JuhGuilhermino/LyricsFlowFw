package com.example.lyricsflowfw.core.domain;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseTask<U extends BaseUser, C extends BaseContent> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private U user;

    // ANTES: private S song;
    // AGORA: Aponta para o conteúdo genérico (pode ser Song ou UserText)
    @ManyToOne
    @JoinColumn(name = "content_id") 
    private C content;

    private Float score;
    
    @Column(columnDefinition = "TEXT")
    private String generatedActivity; // O corpo da atividade gerada pela IA

    @Column(columnDefinition = "TEXT")
    private String answerKey;

    public BaseTask(Long id, U user, C content, Float score, String generatedActivity, String answerKey) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.score = score;
        this.generatedActivity = generatedActivity;
        this.answerKey = answerKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public U getUser() {
        return user;
    }

    public void setUser(U user) {
        this.user = user;
    }

    public C getContent() {
        return content;
    }

    public void setContent(C content) {
        this.content = content;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getGeneratedActivity() {
        return generatedActivity;
    }

    public void setGeneratedActivity(String generatedActivity) {
        this.generatedActivity = generatedActivity;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }
}
