package com.Task.Manager.repository;

import com.Task.Manager.entity.Task;
import com.Task.Manager.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public Optional<List<Task>> findByUser(TaskUser user);

}
