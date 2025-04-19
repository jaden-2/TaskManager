package com.Task.Manager.DTO;

import com.Task.Manager.entity.TaskUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserResponseDTO {
    private String username;
    private String email;
    private String role;

    public UserResponseDTO (TaskUser user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
