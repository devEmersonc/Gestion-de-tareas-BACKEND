package com.devEmersonc.gestion_tareas.service;

import com.devEmersonc.gestion_tareas.dto.RegisterUserDTO;
import com.devEmersonc.gestion_tareas.dto.UserDTO;
import com.devEmersonc.gestion_tareas.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUser(Long id);
    void saveUser(RegisterUserDTO registerUserDTO);
    void saveUserAdmin(RegisterUserDTO registerUserDTO);
    void updateUser(Long id, RegisterUserDTO registerUserDTO);
    void deleteUser(Long id);
    User getCurrentUser(String username);
    UserDTO convertEntityToDto(User user);
}
