package com.devEmersonc.gestion_tareas.service.serviceImpl;

import com.devEmersonc.gestion_tareas.dto.AuthRequest;
import com.devEmersonc.gestion_tareas.dto.AuthResponse;
import com.devEmersonc.gestion_tareas.service.AuthService;
import com.devEmersonc.gestion_tareas.service.CustomUserDetailsService;
import com.devEmersonc.gestion_tareas.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());

        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
