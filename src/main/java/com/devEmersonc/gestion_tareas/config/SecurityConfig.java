package com.devEmersonc.gestion_tareas.config;

import com.devEmersonc.gestion_tareas.security.JwtAuthenticationEntryPoint;
import com.devEmersonc.gestion_tareas.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtFilter jwtFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users/save-normal-user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/save-admin-user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tasks/save-task").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/tasks").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/tasks/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/tasks/task/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/{id}").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
