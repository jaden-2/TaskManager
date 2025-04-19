package com.Task.Manager.DTO;

import com.Task.Manager.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class TaskResponseDTO {
    private Long taskId;
    @NotNull
    private String title;
    private String description;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT +1")
    private LocalDate deadline;
    @NotNull
    private Task.Status status;

    public TaskResponseDTO(Task task){
        this.taskId = task.getTaskId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
        this.status = task.getStatus();
    }
}
