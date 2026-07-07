package com.example.lyricsflowfw.core.service;

import com.example.lyricsflowfw.core.domain.BaseContent;
import java.util.Optional;

public interface ExternalContentProvider<C extends BaseContent> {
    
    /**
     * Consome conteúdo de fontes variadas de forma flexível.
     * * @param sourceIdentifier O identificador principal (Pode ser o título da música, a URL de uma API, ou o próprio Texto Bruto enviado pelo usuário)
     * @param sourceType O tipo da fonte de origem (ex: "API_GENIUS", "USER_INPUT", "LOCAL_FILE", "EXTERNAL_DB")
     * @param extraParams Parâmetros adicionais específicos de cada provedor (ex: nome do artista, token de autenticação, formato do arquivo)
     */
    Optional<C> fetchExternalContent(String sourceIdentifier, String sourceType, String... extraParams);

    /**
     * Verifica se o provedor em questão consegue lidar com determinado tipo de fonte.
     */
    boolean supportsSource(String sourceType);
}
