package com.example.application1.repository;

import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.application1.model.Flashcard;
import java.util.Optional;
import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findDueCardsByUserId(@Param("userId") Long userId, @Param("today") LocalDate today);
    boolean existsByUserIdAndWordIgnoreCase(Long userId, String word);
    Optional<Flashcard> findByUserIdAndWordIgnoreCase(Long userId, String word);
    List<Flashcard> findByUserId(Long userId);
}
