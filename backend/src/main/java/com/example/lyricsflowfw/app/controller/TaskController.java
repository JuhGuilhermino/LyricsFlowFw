/*
package com.example.lyricsflowfw.app.controller;

import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.app.model.Task;
import com.example.lyricsflowfw.app.model.User;
import com.example.lyricsflowfw.app.repository.SongRepository;
import com.example.lyricsflowfw.app.repository.UserRepository;
import com.example.lyricsflowfw.app.service.TaskService;
import com.example.lyricsflowfw.core.domain.LearningProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    // Injeção de dependências via construtor
    public TaskController(TaskService taskService, UserRepository userRepository, SongRepository songRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @PostMapping("/test-generation")
    public ResponseEntity<?> testTaskGeneration(
            @RequestParam Long userId,
            @RequestParam Long songId,
            @RequestBody LearningProfile profile) {
        
        try {
            // 1. Recupera o usuário e a música fictícios que você já inseriu no banco para o teste
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + userId + " não encontrado."));

            Song song = songRepository.findById(songId)
                    .orElseThrow(() -> new IllegalArgumentException("Música com ID " + songId + " não encontrada."));

            // 2. Aciona o serviço que orquestra a estratégia GapFillingTaskStrategy com o Gemini
            Task generatedTask = taskService.generateNewTaskWithGemini(user, song, profile);

            // 3. Retorna a tarefa montada e persistida com HTTP 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(generatedTask);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a requisição de IA: " + e.getMessage());
        }
    }
}
*/




/**
package com.example.lyricsflowfw.app.controller;

import com.example.lyricsflowfw.app.dto.TaskGenerateResponseDTO;
import com.example.lyricsflowfw.app.dto.TaskStartRequestDTO;
import com.example.lyricsflowfw.app.dto.TaskSubmissionDTO;
import com.example.lyricsflowfw.app.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startTask(@RequestBody TaskStartRequestDTO request) {
        try {
            TaskGenerateResponseDTO exercise = this.taskService.generateTask(request.getUserId(), request.getSongId());
            return ResponseEntity.ok(exercise);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao gerar o exercício: " + e.getMessage());
        }
    }

    
    @PostMapping("/submit")
    public ResponseEntity<String> submitTask(@RequestBody TaskSubmissionDTO submission) {
        try {
            this.taskService.submitTask(submission);
            return ResponseEntity.ok("Exercício enviado com sucesso. Pontuação calculada e flashcards gerados.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao enviar o exercício: " + e.getMessage());
        }
    }
    
    @PostMapping("/music")
    public ResponseEntity<?> findOrSaveMusic(@RequestBody MusicRequestDTO request) {
        try {
            MusicResponseDTO response = this.taskService.searchSong(request.getTitle(), request.getArtist());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a requisição da música: " + e.getMessage());
        }
    }
    */
