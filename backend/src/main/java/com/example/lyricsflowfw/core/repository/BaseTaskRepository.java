package com.example.lyricsflowfw.core.repository;

import com.example.lyricsflowfw.core.domain.BaseTask;
import com.example.lyricsflowfw.core.domain.BaseUser;
import com.example.lyricsflowfw.core.domain.BaseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean // Garante que o Spring não tentará criar um Bean desta interface genérica
public interface BaseTaskRepository<T extends BaseTask<U, C>, U extends BaseUser, C extends BaseContent> 
        extends JpaRepository<T, Long> {
    
    // Busca todas as tarefas pelo ID do usuário (parâmetro fixo)
    List<T> findByUserId(Long userId);

    // Busca a tarefa combinando o ID do usuário e o ID da música (ambos mapeados na BaseTask)
    Optional<T> findByUserIdAndContentId(Long userId, Long contentId);
}
