package com.example.application1.service;

import com.example.application1.dto.FlashcardAnswerDTO;
import com.example.application1.dto.FlashcardDetailsResponseDTO;
import com.framework.learning_core.domain.EnumFlashcardAnswerQuality;
import com.example.application1.model.Flashcard;
import com.example.application1.repository.FlashcardRepository;
import com.example.application1.client.GeminiClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final GeminiClient geminiClient;

    public FlashcardService(FlashcardRepository flashcardRepository, GeminiClient geminiClient) {
        this.flashcardRepository = flashcardRepository;
        this.geminiClient = geminiClient;
    }


    public FlashcardDetailsResponseDTO getNextFlashcardForReview(Long userId) {
        List<Flashcard> dueCards = flashcardRepository.findDueCardsByUserId(userId, LocalDate.now());

        if (dueCards.isEmpty()) {
            throw new IllegalArgumentException("Nenhum flashcard pendente para revisão do usuário " + userId);
        }

        Flashcard nextCard = dueCards.get(0);

        FlashcardDetailsResponseDTO dynamicContent = geminiClient.generateFlashcardDetails(nextCard.getWord());

        if (dynamicContent != null) {
            dynamicContent.setFlashcardId(nextCard.getId());
        }

        return dynamicContent;
    }


    @Transactional
    public void processAnswer(FlashcardAnswerDTO answerDTO) {
        Flashcard card = this.flashcardRepository.findById(answerDTO.getFlashcardId())
                .orElseThrow(() -> new IllegalArgumentException("Flashcard não encontrado com o ID: " + answerDTO.getFlashcardId()));

        EnumFlashcardAnswerQuality qualityEnum;
        try {
            qualityEnum = EnumFlashcardAnswerQuality.valueOf(answerDTO.getQuality().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Qualidade inválida fornecida: " + answerDTO.getQuality());
        }


        applySM2(card, qualityEnum);
        this.flashcardRepository.save(card);
    }


    private void applySM2(Flashcard card, EnumFlashcardAnswerQuality quality) {
        int q = switch (quality) {
            case PERFECT   -> 5;
            case EASY      -> 4;
            case MEDIUM    -> 3;
            case HARD      -> 2;
            case INCORRECT -> 1;
            case BLACKOUT  -> 0;
        };

        double ef = card.getEaseFactor() != null ? card.getEaseFactor() : 2.5;
        int interval = card.getInterval() != null ? card.getInterval() : 0;

        if (q >= 3) {
            interval = switch (interval) {
                case 0 -> 1;
                case 1 -> 6;
                default -> (int) Math.round(interval * ef);
            };
        } else {
            interval = 1;
        }

        ef = ef + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02));
        ef = Math.max(1.3, ef); 

        card.setInterval(interval);
        card.setEaseFactor((float) ef);
        card.setLastQuality(quality);
        card.setNextReviewDate(LocalDate.now().plusDays(interval));
    }


    public List<Flashcard> getAllFlashcardsByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        
        try {
            return this.flashcardRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao consultar os flashcards no banco de dados", e);
        }
    }


    @Transactional
    public void reviewSpecificWord(Long userId, String word, String quality) {
        if (userId == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        if (word == null || word.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra pesquisada não pode ser vazia.");
        }

        Flashcard card = this.flashcardRepository.findByUserIdAndWordIgnoreCase(userId, word.trim())
                .orElseThrow(() -> new IllegalArgumentException("Nenhum flashcard encontrado para a palavra '" + word + "' neste usuário."));

        EnumFlashcardAnswerQuality qualityEnum;
        try {
            qualityEnum = EnumFlashcardAnswerQuality.valueOf(quality.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Qualidade inválida fornecida: " + quality);
        }

        applySM2(card, qualityEnum);

        this.flashcardRepository.save(card);
        
        System.out.println("Revisão isolada da palavra '" + card.getWord() + "' processada! Próxima revisão: " + card.getNextReviewDate());
    }

}
