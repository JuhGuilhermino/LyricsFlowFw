package com.example.application1.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.application1.model.Flashcard;
//import com.example.application1.model.Song;
import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByUserIdAndNextReviewDateLessThanEqual(Long userId, LocalDate date);
    boolean existsByUserIdAndWordIgnoreCase(Long userId, String word);
}
