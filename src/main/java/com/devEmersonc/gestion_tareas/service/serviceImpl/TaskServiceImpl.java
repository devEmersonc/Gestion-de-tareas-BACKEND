package com.devEmersonc.gestion_tareas.service.serviceImpl;

import com.devEmersonc.gestion_tareas.dto.SaveTaskDTO;
import com.devEmersonc.gestion_tareas.dto.TaskDTO;
import com.devEmersonc.gestion_tareas.exception.TaskNotFoundException;
import com.devEmersonc.gestion_tareas.exception.UnauthorizedException;
import com.devEmersonc.gestion_tareas.exception.UserNotFoundException;
import com.devEmersonc.gestion_tareas.model.Task;
import com.devEmersonc.gestion_tareas.model.User;
import com.devEmersonc.gestion_tareas.repository.TaskRepository;
import com.devEmersonc.gestion_tareas.repository.UserRepository;
import com.devEmersonc.gestion_tareas.service.TaskService;
import com.devEmersonc.gestion_tareas.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        List<Task> tasks = taskRepository.findByUserId(user.getId());

        if(tasks.isEmpty()) {
            throw new TaskNotFoundException("El usuario ingresado no tiene tareas asociadas.");
        }

        return tasks.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaksById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("La tarea ingresada no existe."));
        return convertEntityToDto(task);
    }

    @Override
    public void saveNewTask(SaveTaskDTO saveTaskDTO, User user) {
        Task task = new Task();
        task.setTitle(saveTaskDTO.getTitle());
        task.setDescription(saveTaskDTO.getDescription());
        task.setPriority(saveTaskDTO.getPriority());
        task.setState(saveTaskDTO.getState());
        task.setExpiration_date(saveTaskDTO.getExpiration_date());
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public void updateUpdateTask(Long id, SaveTaskDTO saveTaskDTO, User user) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("La tarea ingresada no existe."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toString();
        User currentUser = user;

        if(role.contains("ROLE_ADMIN")) {
            task.setTitle(saveTaskDTO.getTitle());
            task.setDescription(saveTaskDTO.getDescription());
            task.setPriority(saveTaskDTO.getPriority());
            task.setState(saveTaskDTO.getState());
            task.setExpiration_date(saveTaskDTO.getExpiration_date());
            taskRepository.save(task);
        } else if(role.contains("ROLE_USER")) {
            if(task.getUser().getId().equals(currentUser.getId())) {
                task.setTitle(saveTaskDTO.getTitle());
                task.setDescription(saveTaskDTO.getDescription());
                task.setPriority(saveTaskDTO.getPriority());
                task.setState(saveTaskDTO.getState());
                task.setExpiration_date(saveTaskDTO.getExpiration_date());
                taskRepository.save(task);
            } else {
                throw new UnauthorizedException("No tienes permisos para realizar esta acción.");
            }
        }

    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("La tarea ingresada no existe."));
        taskRepository.deleteById(task.getId());
    }

    @Override
    public TaskDTO convertEntityToDto(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setState(task.getState());
        taskDTO.setExpiration_date(task.getExpiration_date());
        return taskDTO;
    }
}
