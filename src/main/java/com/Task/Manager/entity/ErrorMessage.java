package com.Task.Manager.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorMessage {
    private String message;
}
