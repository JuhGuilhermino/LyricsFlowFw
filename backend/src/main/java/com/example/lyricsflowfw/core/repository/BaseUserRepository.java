package com.example.lyricsflowfw.core.repository;

import com.example.lyricsflowfw.core.domain.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.Optional;

@NoRepositoryBean // Impede o Spring de tentar criar uma instância direta desta interface
public interface BaseUserRepository<U extends BaseUser> extends JpaRepository<U, Long> {
    
    Optional<U> findByEmail(String email); 
    
    boolean existsByEmail(String email); 
    
    boolean existsByUsername(String username); 
}

