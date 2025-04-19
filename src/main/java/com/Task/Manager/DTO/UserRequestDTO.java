package com.Task.Manager.DTO;

import com.Task.Manager.entity.TaskUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String roles;

}
