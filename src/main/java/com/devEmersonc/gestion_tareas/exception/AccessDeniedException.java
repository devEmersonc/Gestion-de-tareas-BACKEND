package com.devEmersonc.gestion_tareas.exception;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message) {
        super(message);
    }
}
