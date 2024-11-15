package com.devEmersonc.gestion_tareas.service;

import com.devEmersonc.gestion_tareas.dto.SaveTaskDTO;
import com.devEmersonc.gestion_tareas.dto.TaskDTO;
import com.devEmersonc.gestion_tareas.dto.UserDTO;
import com.devEmersonc.gestion_tareas.model.Task;
import com.devEmersonc.gestion_tareas.model.User;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getAllTasks();
    List<TaskDTO> getTasksByUserId(Long userId);
    TaskDTO getTaksById(Long id);
    void saveNewTask(SaveTaskDTO saveTaskDTO, User user);
    void updateUpdateTask(Long id, SaveTaskDTO saveTaskDTO, User user);
    void deleteTask(Long id);
    TaskDTO convertEntityToDto(Task task);
}
