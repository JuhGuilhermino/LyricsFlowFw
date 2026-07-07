package com.example.lyricsflowfw.core.repository;

import com.example.lyricsflowfw.core.domain.BaseTask;
import com.example.lyricsflowfw.core.domain.BaseUser;
import com.example.lyricsflowfw.core.domain.BaseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseTaskRepository<T extends BaseTask<U, C>, U extends BaseUser, C extends BaseContent> 
        extends JpaRepository<T, Long> {
    
    List<T> findByUserId(Long userId);

    // ADICIONADO: "OrderByIdDesc" garante pegar a atividade mais recente do cache se houver duplicidade
    Optional<T> findFirstByUserIdAndContentIdOrderByIdDesc(Long userId, Long contentId);
}
