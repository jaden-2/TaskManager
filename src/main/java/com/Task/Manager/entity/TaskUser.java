package com.Task.Manager.entity;

import com.Task.Manager.DTO.UserRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "user", schema = "taskManager")
public class TaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull(message = "User should have a username")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull(message = "User should have an email address")
    @Email(message = "Invalid Email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull(message = "Provide password")
    private String password;

    @NotNull
    private String role;

}
