package com.example.application1.service;

import com.example.application1.dto.LoginRequestDTO;
import com.example.application1.dto.UserRegisterRequestDTO;
import com.example.application1.dto.UserRegisterResponseDTO;
import com.example.application1.dto.UserResponseDTO;
import com.example.application1.model.User;
import com.example.application1.model.UserProgress;
import com.example.application1.repository.UserRepository;
import com.example.application1.repository.UserProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import java.util.Optional;

@Service
public class UserService { 
    private final UserRepository userRepository;
    private final UserProgressRepository userProgressRepository;

    public UserService(UserRepository userRepository, UserProgressRepository userProgressRepository) {
        this.userRepository = userRepository;
        this.userProgressRepository = userProgressRepository;
    }

    @Transactional
    public UserRegisterResponseDTO register(UserRegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Nome de usuário já está em uso.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); 
        user.setCurrentLevelEnum(request.getLevel());
        User savedUser = userRepository.save(user);

        UserProgress progress = new UserProgress();
        progress.setUser(savedUser);
        progress.setTotalTasksCompleted(0);
        progress.setAverageTaskScore(0.0f);
        progress.setTotalTargetWords(0);
        progress.setTotalFlashcardsCount(0);
        this.userProgressRepository.save(progress);

        return new UserRegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCurrentLevelEnum()
        );
    }

    public UserResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas (E-mail ou senha incorretos)."));

        if (request.getPassword() == user.getPassword()) {
            throw new IllegalArgumentException("Credenciais inválidas (E-mail ou senha incorretos).");
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCurrentLevelEnum()
        );
    }
}
