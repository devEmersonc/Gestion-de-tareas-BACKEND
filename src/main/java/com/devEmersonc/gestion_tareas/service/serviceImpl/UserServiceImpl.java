package com.devEmersonc.gestion_tareas.service.serviceImpl;

import com.devEmersonc.gestion_tareas.dto.RegisterUserDTO;
import com.devEmersonc.gestion_tareas.dto.UserDTO;
import com.devEmersonc.gestion_tareas.exception.UserNotFoundException;
import com.devEmersonc.gestion_tareas.model.User;
import com.devEmersonc.gestion_tareas.repository.RoleRepository;
import com.devEmersonc.gestion_tareas.repository.UserRepository;
import com.devEmersonc.gestion_tareas.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return this.convertEntityToDto(user);
    }

    @Override
    public void saveUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setEmail(registerUserDTO.getEmail());
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }

    @Override
    public void saveUserAdmin(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setEmail(registerUserDTO.getEmail());
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, RegisterUserDTO registerUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setEmail(registerUserDTO.getEmail());
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteById(user.getId());
    }

    @Override
    public User getCurrentUser(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public UserDTO convertEntityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
