package com.example.lyricsflowfw.core.repository; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import com.example.lyricsflowfw.core.domain.BaseFlashcard; 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseFlashcardRepository<T extends BaseFlashcard, ID> extends JpaRepository<T, ID> {
    
    List<T> findDueCardsByUserId(@Param("userId") Long userId, @Param("today") LocalDate today);
    boolean existsByUserIdAndWordIgnoreCase(Long userId, String word);
    Optional<T> findByUserIdAndWordIgnoreCase(Long userId, String word);
    List<T> findByUserId(Long userId);
}