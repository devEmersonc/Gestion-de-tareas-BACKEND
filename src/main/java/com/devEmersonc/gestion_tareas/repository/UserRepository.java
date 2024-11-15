package com.devEmersonc.gestion_tareas.repository;

import com.devEmersonc.gestion_tareas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
