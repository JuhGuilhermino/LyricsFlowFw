package com.example.application1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.application1.enums.LanguageLevel;
import com.framework.learning_core.domain.BaseUser;

@Entity
@Table(name = "users")
public class User extends BaseUser {

    // A senha e detalhes de segurança ficam na aplicação concreta
    @Column(nullable = false)
    private String password;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(LocalDateTime.now());
    }

    // Construtor Padrão exigido pelo JPA
    public User() {
        super();
    }

    // Construtor Completo mapeando os parâmetros para a classe abstrata do framework
    public User(Long id, String username, String email, String password, String avatarPath, LanguageLevel currentLevel, LocalDateTime createdAt) {
        super(id, username, email, avatarPath, currentLevel != null ? currentLevel.name() : null, createdAt);
        this.password = password;
    }

    // Mapeamento das anotações nos métodos Getter (estratégia limpa para herança JPA)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() { return super.getId(); }

    @Column(unique = true, nullable = false)
    @Override
    public String getUsername() { return super.getUsername(); }

    @Column(unique = true, nullable = false)
    @Override
    public String getEmail() { return super.getEmail(); }

    // Getter e Setter específicos da aplicação concreta
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Métodos de conveniência para a sua aplicação continuar usando o Enum sem quebrar nada
    @Transient // Informa ao JPA para não tentar salvar este método no banco
    public LanguageLevel getCurrentLevelEnum() {
        return this.getCurrentLevel() != null ? LanguageLevel.valueOf(this.getCurrentLevel()) : null;
    }

    public void setCurrentLevelEnum(LanguageLevel level) {
        this.setCurrentLevel(level != null ? level.name() : null);
    }
}