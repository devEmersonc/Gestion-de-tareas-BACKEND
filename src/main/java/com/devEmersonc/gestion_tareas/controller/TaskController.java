package com.devEmersonc.gestion_tareas.controller;

import com.devEmersonc.gestion_tareas.dto.SaveTaskDTO;
import com.devEmersonc.gestion_tareas.dto.TaskDTO;
import com.devEmersonc.gestion_tareas.dto.UserDTO;
import com.devEmersonc.gestion_tareas.model.User;
import com.devEmersonc.gestion_tareas.service.TaskService;
import com.devEmersonc.gestion_tareas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaksById(id);
        return ResponseEntity.ok(taskDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable Long id) {
        List<TaskDTO> tasks = taskService.getTasksByUserId(id);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/save-task")
    public ResponseEntity<String> saveTask(@Valid @RequestBody SaveTaskDTO saveTaskDTO, Principal principal) {
        User user = userService.getCurrentUser(principal.getName());
        taskService.saveNewTask(saveTaskDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Nueva tarea guardada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@Valid @RequestBody SaveTaskDTO saveTaskDTO, @PathVariable Long id, Principal principal) {
        User user = userService.getCurrentUser(principal.getName());
        taskService.updateUpdateTask(id, saveTaskDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tarea actualizada exitosamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, Principal principal) {
        User currentUser = userService.getCurrentUser(principal.getName());
        taskService.deleteTask(id, currentUser);
        return ResponseEntity.ok("Tarea eliminada exitosamente.");
    }
}
