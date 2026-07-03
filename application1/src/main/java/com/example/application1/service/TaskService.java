package com.example.application1.service;

import com.example.application1.client.GeminiClient;
import com.example.application1.dto.*;
import com.example.application1.model.*;
import com.example.application1.repository.*;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final FlashcardRepository flashcardRepository;
    private final GeminiClient geminiClient;


    public TaskService(TaskRepository taskRepository, 
                       UserRepository userRepository, 
                       SongRepository songRepository, 
                       FlashcardRepository flashcardRepository,
                       GeminiClient geminiClient) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.flashcardRepository = flashcardRepository;
        this.geminiClient = geminiClient;
    }

    
    public List<MusicResponseDTO> listAllSongs() {
        List<Song> songs = this.songRepository.findAll();

        return songs.stream().map(song -> {
            MusicResponseDTO dto = new MusicResponseDTO();
            dto.setId(song.getId());
            dto.setTitle(song.getTitle());
            dto.setArtist(song.getArtist());
            dto.setLyrics(song.getLyrics());
            return dto;
        }).collect(Collectors.toList());
    }


    private TaskGenerateResponseDTO getExistingTaskFromDatabase(Task task) {
        TaskGenerateResponseDTO cachedTask = new TaskGenerateResponseDTO();
        cachedTask.setTaskId(task.getId());
        cachedTask.setMaskedLyrics(task.getMaskedLyrics());
        cachedTask.setTargetWords(task.getTargetWords());
        return cachedTask;
    }


    private TaskGenerateResponseDTO generateNewTaskWithGemini(User user, Song song) {
        String userLevel = user.getCurrentLevel() != null ? user.getCurrentLevel() : "BEGINNER";
        
        TaskGenerateResponseDTO exercise = this.geminiClient.generateTask(song.getLyrics(), userLevel);

        if (exercise == null) {
            throw new RuntimeException("Falha ao gerar o exercício com o Gemini.");
        }

        Task newTask = new Task();
        newTask.setUser(user);
        newTask.setSong(song);
        newTask.setScore(0.0f);
        newTask.setMaskedLyrics(exercise.getMaskedLyrics());
        newTask.setTargetWords(exercise.getTargetWords());
        newTask.setCompletedAt(null);

        Task savedTask = this.taskRepository.save(newTask);

        exercise.setTaskId(savedTask.getId());

        return exercise;
    }


    @Transactional
    public TaskGenerateResponseDTO generateTask(Long userId, Long songId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        Optional<Song> songOptional = this.songRepository.findById(songId);
    
        if (userOptional.isEmpty() || songOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário ou Música não encontrados.");
        }

        User user = userOptional.get();
        Song song = songOptional.get();

        Optional<Task> existingTask = this.taskRepository.findByUserIdAndSongId(userId, songId);
        if (existingTask.isPresent()) {
            return getExistingTaskFromDatabase(existingTask.get());
        }

        return generateNewTaskWithGemini(user, song);
    }

    
    private float calculateScore(List<String> answerKey, List<String> userAnswers) {
        if (answerKey == null || answerKey.isEmpty()) {
            return 0.0f;
        }

        int totalQuestions = answerKey.size();
        int correctCount = 0;

        for (int i = 0; i < totalQuestions; i++) {
            if (userAnswers != null && i < userAnswers.size() && userAnswers.get(i) != null) {
                String cleanUserAnswer = userAnswers.get(i).trim().toLowerCase();
                String cleanCorrectAnswer = answerKey.get(i).trim().toLowerCase();

                if (cleanUserAnswer.equals(cleanCorrectAnswer)) {
                    correctCount++;
                }
            }
        }

        return ((float) correctCount / totalQuestions) * 10.0f;
    }


    private void updateTaskStatus(Task task, float score) {
        task.setScore(score);
        task.setCompletedAt(LocalDateTime.now());
        this.taskRepository.save(task);
    }


    private void createFlashcardsForTask(User user, List<String> answerKey) {
        if (answerKey == null || answerKey.isEmpty()) {
            return;
        }

        for (String word : answerKey) {
            if (word == null) continue;

            String cleanWord = word.trim();

            boolean alreadyExists = this.flashcardRepository.existsByUserIdAndWordIgnoreCase(user.getId(), cleanWord);

            if (!alreadyExists) {
                Flashcard flashcard = new Flashcard();
                flashcard.setUser(user);
                flashcard.setWord(cleanWord);
                flashcard.setInterval(1); 
                flashcard.setNextReviewDate(LocalDate.now().plusDays(1));
                flashcard.setEaseFactor(2.5f); 
                flashcard.setLastQuality(null);
                flashcard.setCreatedAt(LocalDateTime.now());

                this.flashcardRepository.save(flashcard);
            }
        }
    }


    public void submitTask(TaskSubmissionDTO submission) {
        Optional<Task> taskOptional = this.taskRepository.findById(submission.getTaskId());
        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Tarefa não encontrada!.");
        }
        Task task = taskOptional.get();
        User user = task.getUser();

        List<String> answerKey = task.getTargetWords();
        List<String> userAnswers = submission.getUserAnswers();

        float finalScore = calculateScore(answerKey, userAnswers);
        updateTaskStatus(task, finalScore);
        createFlashcardsForTask(user, answerKey);
    }


}
