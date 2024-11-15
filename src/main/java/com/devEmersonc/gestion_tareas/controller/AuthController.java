package com.devEmersonc.gestion_tareas.controller;

import com.devEmersonc.gestion_tareas.dto.AuthRequest;
import com.devEmersonc.gestion_tareas.dto.AuthResponse;
import com.devEmersonc.gestion_tareas.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}