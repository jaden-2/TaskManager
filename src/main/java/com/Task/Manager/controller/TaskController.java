package com.Task.Manager.controller;

import com.Task.Manager.DTO.TaskRequestDTO;
import com.Task.Manager.DTO.TaskResponseDTO;
import com.Task.Manager.entity.TaskUserDetail;
import com.Task.Manager.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService service){
        this.taskService = service;
    }

    @PostMapping("/task")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO task, @AuthenticationPrincipal TaskUserDetail userDetail, HttpServletRequest request){
        TaskResponseDTO createdTask = taskService.createTask(task, userDetail.getUser());
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(createdTask);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponseDTO> getTasks(@PathVariable(required = false) Long id, @AuthenticationPrincipal TaskUserDetail userDetail){

        return ResponseEntity.ok(taskService.getTask(id));
    }
    @GetMapping("/task")
    public ResponseEntity<List<TaskResponseDTO>> getTask(@AuthenticationPrincipal TaskUserDetail taskUserDetail){
        return ResponseEntity.ok(taskService.getAllTask(taskUserDetail.getUser()));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO updatedTask, @AuthenticationPrincipal TaskUserDetail userDetail, HttpServletRequest request){
        taskService.updateTask(updatedTask, userDetail.getUser(), id);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(taskService.getTask(id));
    }

    @DeleteMapping("/task/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }
}
