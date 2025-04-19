package com.Task.Manager.service;

import com.Task.Manager.DTO.TaskRequestDTO;
import com.Task.Manager.DTO.TaskResponseDTO;
import com.Task.Manager.customExceptions.UnauthenticatedOperationException;
import com.Task.Manager.customExceptions.InvalidTaskException;
import com.Task.Manager.entity.Task;
import com.Task.Manager.entity.TaskUser;
import com.Task.Manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository repository;
    @Autowired
    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO task, TaskUser user){
        Task newTask= new Task(task, user);
        repository.save(newTask);
        return  new TaskResponseDTO(newTask);
    }

    // Implement DTO so avoid sending User entity along with Task
    // Server now returns the data transfer objects for Task to prevent exposing user information
    public List<TaskResponseDTO> getAllTask(TaskUser user){
        List<Task> tasks = repository.findByUser(user).orElseThrow(()->new InvalidTaskException("User does not have tasks"));
        return tasks.stream().map(TaskResponseDTO::new).toList();
    }

    public TaskResponseDTO getTask(Long id){
        return new TaskResponseDTO(repository.findById(id).orElseThrow(()-> new InvalidTaskException("Task does not exist")));
    }
    // --------------------------------end here-----------------------------------

    public void updateTask(TaskRequestDTO updatedTask, TaskUser user, long id){
        Task modifiedTask = repository.findById(id).orElseThrow(()->new InvalidTaskException("Cannot update a task that does not exist"));
        modifiedTask.setTitle(updatedTask.getTitle());
        modifiedTask.setDeadline(updatedTask.getDeadline());
        modifiedTask.setDescription(updatedTask.getDescription());
        modifiedTask.setStatus(updatedTask.getStatus());

        // Ensure that request does not modify another users task
        if(!Objects.equals(modifiedTask.getUser().getUserId(), user.getUserId()))
            throw new UnauthenticatedOperationException("This operation is invalid");
        repository.save(modifiedTask);
    }

    public void deleteTask(Long id){
        repository.deleteById(id);
    }

}
