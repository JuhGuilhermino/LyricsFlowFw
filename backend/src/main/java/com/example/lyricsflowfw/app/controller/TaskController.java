package com.example.lyricsflowfw.app.controller;

import com.example.lyricsflowfw.app.dto.TaskStartRequestDTO;
import com.example.lyricsflowfw.app.model.Task;
import com.example.lyricsflowfw.app.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    // Repositórios removidos daqui, pois a orquestração agora é 100% responsabilidade do Service
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startTask(@RequestBody TaskStartRequestDTO request) {
        try {
            // Validação simples defensiva
            if (request.getProfile() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O perfil de aprendizado (profile) com o nível de idioma é obrigatório.");
            }

            // Invoca o service passando os três parâmetros necessários da nova arquitetura
            Task exercise = this.taskService.generateTask(
                request.getUserId(), 
                request.getSongId(), 
                request.getProfile()
            );

            // Retorna o exercício gerado (ou recuperado do banco) com status 200 OK
            return ResponseEntity.ok(exercise);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao gerar o exercício: " + e.getMessage());
        }
    }

    /**
     * Endpoint para Submissão, Correção e Encerramento do Exercício.
     * Recebe as respostas do usuário, calcula a nota e retorna o resultado detalhado.
     */
    @PostMapping("/{taskId}/submit")
    public ResponseEntity<?> submitTaskResponse(
            @PathVariable Long taskId,
            @RequestBody List<String> userAnswers) {
        try {
            if (userAnswers == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A lista de respostas (userAnswers) não pode ser nula.");
            }

            // 1. Invoca a regra de negócio completa (calcula, salva e gera flashcards)
            // Certifique-se de que o seu método submitTask no Service foi atualizado para retornar a Task (ou o score float)
            Task updatedTask = this.taskService.submitTask(taskId, userAnswers);

            // 2. Monta uma estrutura JSON limpa para responder ao usuário/frontend
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("taskId", taskId);
            response.put("score", updatedTask.getScore()); // Exibe o escore final (ex: 7.5)
            response.put("status", "COMPLETED");
            response.put("message", "Respostas corrigidas e pontuadas com sucesso!");

            // 3. Retorna os dados com HTTP 200 OK
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro ao processar a correção da tarefa: " + e.getMessage());
        }
    }
}

