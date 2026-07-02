package com.example.application1.controller;

import com.example.application1.dto.MusicRequestDTO;
import com.example.application1.dto.MusicResponseDTO;
import com.example.application1.dto.TaskGenerateResponseDTO;
import com.example.application1.dto.TaskStartRequestDTO;
import com.example.application1.dto.TaskSubmissionDTO;
import com.example.application1.service.TaskService;
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

    @GetMapping
    public ResponseEntity<?> getAllSongs() {
        try {
            List<MusicResponseDTO> songs = this.taskService.listAllSongs();
            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao listar as músicas: " + e.getMessage());
        }
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

    /**    
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
}
