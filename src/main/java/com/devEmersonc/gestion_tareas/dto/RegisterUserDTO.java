package com.devEmersonc.gestion_tareas.dto;

import com.devEmersonc.gestion_tareas.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RegisterUserDTO {
    @NotNull(message = "El nombre de usuario no puede ser null.")
    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(min = 5, max = 20, message = "El nombre de usuario debe tener entre 5 a 20 caracteres.")
    private String username;
    @NotNull(message = "La contraseña no puede ser null.")
    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;
    @Email(message = "Ingresa un email válido.")
    @NotBlank(message = "El email es obligatorio.")
    @NotNull(message = "El email no puede ser null.")
    private String email;
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
