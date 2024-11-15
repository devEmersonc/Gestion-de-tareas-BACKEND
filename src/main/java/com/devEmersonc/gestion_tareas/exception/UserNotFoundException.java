package com.devEmersonc.gestion_tareas.exception;

public class UserNotFoundException extends ResourceNotFoundException{

    public UserNotFoundException() {
        super("No se encontró al usuario.");
    }
}
