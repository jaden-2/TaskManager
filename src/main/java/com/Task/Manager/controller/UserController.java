package com.Task.Manager.controller;

import com.Task.Manager.DTO.UserRequestDTO;
import com.Task.Manager.DTO.UserResponseDTO;
import com.Task.Manager.customExceptions.CredentialChangedException;
import com.Task.Manager.entity.TaskUser;
import com.Task.Manager.entity.TaskUserDetail;
import com.Task.Manager.service.TaskUserService;
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
@RequestMapping("/api")
public class UserController {
    private final TaskUserService taskUserService;

    @Autowired
    public UserController(TaskUserService taskUserService){
        this.taskUserService = taskUserService;
    }
    // A get request to user does not need the id path variable because we do not want
    // an authenticated user to have access to ever other user in the DB
    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal TaskUserDetail userDetail){
        return ResponseEntity.ok(taskUserService.getUser(userDetail));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody TaskUser newUser, HttpServletRequest request){
        taskUserService.createUser(newUser);

        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new UserResponseDTO(newUser));
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDTO> updateUserRecord(@RequestBody UserRequestDTO updatedUser, @AuthenticationPrincipal TaskUserDetail userDetail, HttpServletRequest request){

        try{
            taskUserService.updateUser(updatedUser, userDetail.getUser());
        } catch (CredentialChangedException e) {

           return ResponseEntity.created(URI.create(request.getRequestURI())).header("X-force-logout", "/logout")
                   .body(new UserResponseDTO(userDetail.getUser())); //returns updated user and tells user to logout
        }
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new UserResponseDTO(userDetail.getUser()));
    }

   @DeleteMapping("/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal TaskUserDetail userDetail){
        taskUserService.deleteUser(userDetail.getUser());
    }
}
