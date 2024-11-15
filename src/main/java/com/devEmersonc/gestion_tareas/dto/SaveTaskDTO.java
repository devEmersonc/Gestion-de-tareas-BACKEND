package com.devEmersonc.gestion_tareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SaveTaskDTO {
    @NotNull(message = "El título no puede ser null.")
    @NotBlank(message = "El título es obligatorio.")
    private String title;
    @NotNull(message = "Descripción no puede ser null.")
    @NotBlank(message = "Descripción es obligatorio.")
    private String description;
    @NotNull(message = "Fecha de vencimiento no puede ser null.")
    @NotBlank(message = "Fecha de vencimiento es obligatoria.")
    private String expiration_date;
    @NotNull(message = "El estado no puede ser null.")
    @NotBlank(message = "El estado es obligatorio.")
    private String state;
    @NotNull(message = "Prioridad no puede ser null.")
    @NotBlank(message = "Prioridad es obligatoria.")
    private String priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
