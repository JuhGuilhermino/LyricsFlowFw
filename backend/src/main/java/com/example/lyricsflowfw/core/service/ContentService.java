package com.example.lyricsflowfw.core.service;

import com.example.lyricsflowfw.core.domain.BaseContent;
import com.example.lyricsflowfw.core.domain.BaseUser;
import com.example.lyricsflowfw.core.repository.BaseContentRepository;
import com.example.lyricsflowfw.core.repository.BaseUserRepository;
import java.util.List;
import java.util.Optional;

public abstract class ContentService<C extends BaseContent, U extends BaseUser> {
    
    protected final BaseUserRepository<U> userRepository;
    protected final BaseContentRepository<C> contentRepository; // Renomeado para refletir o domínio genérico
    protected final List<ExternalContentProvider<C>> contentProviders;

    // Construtor aceitando o provedor externo genérico
    public ContentService(BaseUserRepository<U> userRepository, 
                              BaseContentRepository<C> contentRepository,
                              List<ExternalContentProvider<C>> contentProviders) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
        this.contentProviders = contentProviders;
    }

    // Método Fixo: Listagem universal de qualquer tipo de conteúdo do banco de dados
    public List<C> findAllContentEntities() {
        return this.contentRepository.findAll();
    }

    // Método Fixo: Busca por ID universal no banco de dados
    public Optional<C> findContentById(Long id) {
        return this.contentRepository.findById(id);
    }

    // Método do Framework que dispara o Ponto Flexível de busca externa (API, arquivos, IAs, etc)
    public Optional<C> searchExternalContent(String sourceIdentifier, String sourceType, String... extraParams) {
        return contentProviders.stream()
            .filter(provider -> provider.supportsSource(sourceType))
            .findFirst()
            .flatMap(provider -> provider.fetchExternalContent(sourceIdentifier, sourceType, extraParams));
    }
}
