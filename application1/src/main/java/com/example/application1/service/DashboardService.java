package com.example.application1.service;

import com.example.application1.dto.DashboardDTO;
import com.example.application1.model.UserProgress;
import com.example.application1.model.User; 
import com.example.application1.repository.UserProgressRepository;
import com.example.application1.repository.UserRepository; 
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository; 

    //construtor 
    public DashboardService(UserProgressRepository userProgressRepository, UserRepository userRepository) {
        this.userProgressRepository = userProgressRepository;
        this.userRepository = userRepository;
    }

    public DashboardDTO getDashboardData(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado")); 

        //busca o progresso. Se não existir, cria, vincula o usuário e salva.
        UserProgress progress = userProgressRepository.findByUserId(userId)
            .orElseGet(() -> {
                UserProgress newProgress = new UserProgress();
                newProgress.setUser(user); 
                newProgress.setTotalTasksCompleted(0);
                newProgress.setAverageTaskScore(0.0f);
                newProgress.setTotalTargetWords(0);
                newProgress.setTotalFlashcardsCount(0);
                return userProgressRepository.save(newProgress); 
            });

        return new DashboardDTO(
                progress.getTotalTasksCompleted() != null ? progress.getTotalTasksCompleted() : 0,
                progress.getAverageTaskScore() != null ? progress.getAverageTaskScore() : 0.0f,
                progress.getTotalTargetWords() != null ? progress.getTotalTargetWords() : 0,
                progress.getTotalFlashcardsCount() != null ? progress.getTotalFlashcardsCount() : 0,
                progress.getUpdatedAt()
        );
    }
}
