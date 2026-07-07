package com.example.lyricsflowfw.core.repository;

import com.example.lyricsflowfw.core.domain.BaseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // Impede o Spring de tentar instanciar esta interface genérica diretamente
public interface BaseContentRepository<C extends BaseContent> extends JpaRepository< C, Long> {
    // Métodos universais compartilhados por todas as aplicações de música ficariam aqui
}
