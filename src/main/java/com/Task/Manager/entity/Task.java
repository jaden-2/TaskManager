package com.Task.Manager.entity;

import com.Task.Manager.DTO.TaskRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name ="task", schema = "taskManager")
public class Task {
    public enum Status {PENDING, DONE}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name="title", nullable = false)
    @NotNull(message = "Provide title of task")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT +1")
    @NotNull(message = "Task deadline is required")
    private LocalDate deadline;

    @Column(nullable = false)
    @NotNull(message = "Task status is required")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @NotNull(message = "User cannot be null")
    private TaskUser user;

    // Create a task object from the DTO, preventing the need to expose the entity and makes for easy object creation
    public Task(TaskRequestDTO taskDTO, TaskUser user){
        this.title = taskDTO.getTitle();
        this.deadline = taskDTO.getDeadline();
        this.description = taskDTO.getDescription();
        this.status = taskDTO.getStatus();
        this.user = user;
    }
}
