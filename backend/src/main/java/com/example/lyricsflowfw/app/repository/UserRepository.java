package com.example.lyricsflowfw.app.repository;

import com.example.lyricsflowfw.app.model.User;
import com.example.lyricsflowfw.core.repository.BaseUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseUserRepository<User> {
    // Métodos customizados específicos desta aplicação (se houverem) podem ser adicionados aqui.
}
