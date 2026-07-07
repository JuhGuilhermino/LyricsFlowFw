package com.example.lyricsflowfw.app.controller;

import com.example.lyricsflowfw.app.dto.TaskGenerationRequestDTO;
import com.example.lyricsflowfw.app.model.Song;
import com.example.lyricsflowfw.app.model.Task;
import com.example.lyricsflowfw.app.model.User;
import com.example.lyricsflowfw.app.repository.SongRepository;
import com.example.lyricsflowfw.app.repository.UserRepository;
import com.example.lyricsflowfw.app.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public TaskController(TaskService taskService, UserRepository userRepository, SongRepository songRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @PostMapping("/task-generation")
    public ResponseEntity<?> testTaskGeneration(@RequestBody TaskGenerationRequestDTO request) {
        try {
            // 1. Recupera os dados de dentro do novo DTO unificado
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + request.userId() + " não encontrado."));

            Song song = songRepository.findById(request.songId())
                    .orElseThrow(() -> new IllegalArgumentException("Música com ID " + request.songId() + " não encontrada."));

            // 2. Passa o profile contido no DTO para o serviço
            Task generatedTask = taskService.generateNewTaskWithGemini(user, song, request.profile());

            return ResponseEntity.status(HttpStatus.CREATED).body(generatedTask);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a requisição de IA: " + e.getMessage());
        }
    }
}

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
