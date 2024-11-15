package com.devEmersonc.gestion_tareas.service;

import com.devEmersonc.gestion_tareas.dto.AuthRequest;
import com.devEmersonc.gestion_tareas.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
