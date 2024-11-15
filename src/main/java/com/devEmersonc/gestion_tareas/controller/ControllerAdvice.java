package com.devEmersonc.gestion_tareas.controller;

import com.devEmersonc.gestion_tareas.dto.ErrorMessage;
import com.devEmersonc.gestion_tareas.exception.TaskNotFoundException;
import com.devEmersonc.gestion_tareas.exception.UnauthorizedException;
import com.devEmersonc.gestion_tareas.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> userNotFoundExceptionHandling(UserNotFoundException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> taskNotFoundExceptionHandling(TaskNotFoundException exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value() + " " + HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> unauthorizedExceptionHandle(UnauthorizedException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.UNAUTHORIZED.value() + " " + HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.put("error", "No autorizado");
        response.put("message", exception.getMessage());
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
