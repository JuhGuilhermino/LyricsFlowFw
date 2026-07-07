package com.example.lyricsflowfw.app.controller;

import com.example.lyricsflowfw.app.dto.LoginRequestDTO;
import com.example.lyricsflowfw.app.dto.UserRegisterRequestDTO;
import com.example.lyricsflowfw.app.dto.UserResponseDTO;
import com.example.lyricsflowfw.app.service.UserService;
import com.example.lyricsflowfw.app.dto.UserRegisterResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController { // PONTO FIXO
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody UserRegisterRequestDTO request) {
        UserRegisterResponseDTO response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO request) {
        UserResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
