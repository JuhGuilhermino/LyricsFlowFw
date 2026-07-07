package com.example.lyricsflowfw.app.repository;

import org.springframework.stereotype.Repository;
import com.example.lyricsflowfw.core.repository.BaseFlashcardRepository; 
import com.example.lyricsflowfw.app.model.Flashcard;

@Repository
public interface FlashcardRepository extends BaseFlashcardRepository<Flashcard, Long> {
    //limpo, herdando todas as assinaturas tipadas do core
}
