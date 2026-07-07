package com.example.lyricsflowfw.app.service;

import com.example.lyricsflowfw.app.client.GapFillingTaskStrategy;
import com.example.lyricsflowfw.app.model.*;
import com.example.lyricsflowfw.app.repository.*;
import com.example.lyricsflowfw.core.domain.BaseLearningProfile;
import com.example.lyricsflowfw.core.dto.AiTaskResponseDTO;
import com.example.lyricsflowfw.core.service.BaseTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService extends BaseTaskService<User, Song, Task> {

    private final TaskRepository concreteTaskRepository;
    private final FlashcardRepository flashcardRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       SongRepository songRepository,
                       FlashcardRepository flashcardRepository,
                       GapFillingTaskStrategy gapFillingTaskStrategy) {
        super(taskRepository, userRepository, songRepository, gapFillingTaskStrategy);
        this.concreteTaskRepository = taskRepository;
        this.flashcardRepository = flashcardRepository;
    }

    // 1) IMPLEMENTAÇÃO DO PONTO FLEXÍVEL DA INSTANCIAÇÃO
    @Override
    protected Task createConcreteTask(User user, Song song, AiTaskResponseDTO aiResponse) {
        return new Task(
            null,
            user,
            song,
            0.0f,
            aiResponse.getGeneratedActivity(),
            aiResponse.getAnswerKey()
        );
    }

    // 2) IMPLEMENTAÇÃO DO PONTO FLEXÍVEL DE CÁLCULO DE SCORE
    @Override
    protected float calculateScore(List<String> answerKey, List<String> userAnswers) {
        if (answerKey == null || answerKey.isEmpty()) {
            System.out.println("[SCORE] Gabarito vazio ou nulo!");
            return 0.0f;
        }

        int totalQuestions = answerKey.size();
        int correctCount = 0;

        System.out.println("[SCORE] Iniciando cálculo. Total de lacunas: " + totalQuestions);
        System.out.println("[SCORE] Respostas do usuário recebidas: " + userAnswers);

        for (int i = 0; i < totalQuestions; i++) {
            String correctAnswer = answerKey.get(i);
            
            // Proteção contra respostas enviadas a menos pelo usuário
            if (userAnswers != null && i < userAnswers.size() && userAnswers.get(i) != null) {
                String cleanUserAnswer = userAnswers.get(i).trim().toLowerCase();
                String cleanCorrectAnswer = correctAnswer.trim().toLowerCase();

                System.out.println("[SCORE] Comparando Posição [" + i + "]: Esperado='" + cleanCorrectAnswer + "' | Enviado='" + cleanUserAnswer + "'");

                if (cleanUserAnswer.equals(cleanCorrectAnswer)) {
                    correctCount++;
                    System.out.println("[SCORE] -> ACERTOU!");
                } else {
                    System.out.println("[SCORE] -> ERROU!");
                }
            } else {
                System.out.println("[SCORE] Posição [" + i + "]: Usuário não enviou resposta para esta lacuna.");
            }
        }

        float finalScore = ((float) correctCount / totalQuestions) * 10.0f;
        System.out.println("[SCORE] Resultado Final do Cálculo: " + finalScore + "/10.0");
        return finalScore;
    }

    // 3) PONTOS FIXOS ADAPTADOS E SEPARADOS DE ACORDO COM O SEU BANCO ORIGINAL
    @Transactional
    public Task generateTask(Long userId, Long songId, BaseLearningProfile profile) {
        User user = ((UserRepository) userRepository).findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        Song song = ((SongRepository) contentRepository).findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Música não encontrada."));

        // CORREÇÃO: Utiliza o método mapeado no BaseTaskRepository ordenado de forma decrescente por ID
        Optional<Task> existingTask = concreteTaskRepository.findFirstByUserIdAndContentIdOrderByIdDesc(userId, songId);
        if (existingTask.isPresent()) {
            return existingTask.get();
        }

        return generateNewTaskWithGemini(user, song, profile);
    }

    @Transactional
    public Task submitTask(Long taskId, List<String> userAnswers) {
        Task task = concreteTaskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada!"));
        
        // Limpeza estendida de metadados residuais que a IA costuma colocar na String do gabarito
        String cleanKey = task.getAnswerKey()
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace("'", "")
                .trim();
                
        List<String> answerKey = Arrays.asList(cleanKey.split(",\\s*"));

        float finalScore = calculateScore(answerKey, userAnswers);
        task.setScore(finalScore);
        
        // CORREÇÃO: Altera de save() para saveAndFlush() forçando a sincronização imediata
        // das colunas herdadas do framework na tabela física do PostgreSQL
        Task savedTask = concreteTaskRepository.saveAndFlush(task); 
        
        createFlashcardsForTask(savedTask.getUser(), answerKey);
        
        return savedTask; 
    }

    private void createFlashcardsForTask(User user, List<String> answerKey) {
        if (answerKey == null || answerKey.isEmpty()) return;

        for (String word : answerKey) {
            if (word == null) continue;
            String cleanWord = word.trim();

            boolean alreadyExists = flashcardRepository.existsByUserIdAndWordIgnoreCase(user.getId(), cleanWord);

            if (!alreadyExists) {
                Flashcard flashcard = new Flashcard();
                flashcard.setUser(user);
                flashcard.setWord(cleanWord);
                flashcard.setInterval(1);
                flashcard.setNextReviewDate(LocalDate.now().plusDays(1));
                flashcard.setEaseFactor(2.5f);
                flashcard.setCreatedAt(LocalDateTime.now());

                flashcardRepository.save(flashcard);
            }
        }
    }
}

