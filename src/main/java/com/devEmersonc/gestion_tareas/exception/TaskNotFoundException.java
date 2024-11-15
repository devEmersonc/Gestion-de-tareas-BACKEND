package com.devEmersonc.gestion_tareas.exception;

public class TaskNotFoundException extends ResourceNotFoundException{

    public TaskNotFoundException(String message) {
        super(message);
    }
}
