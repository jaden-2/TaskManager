package com.Task.Manager.repository;

import com.Task.Manager.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TaskUser, Integer> {
        public Optional<TaskUser> findByUsername(String username);
}
