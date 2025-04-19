package com.Task.Manager.service;

import com.Task.Manager.DTO.UserRequestDTO;
import com.Task.Manager.DTO.UserResponseDTO;
import com.Task.Manager.customExceptions.CredentialChangedException;
import com.Task.Manager.entity.TaskUser;
import com.Task.Manager.entity.TaskUserDetail;
import com.Task.Manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PreAuthorize("isAuthenticated()")
public class TaskUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public TaskUserService(UserRepository repository, PasswordEncoder encoder){
        this.repository = repository;
        this.passwordEncoder = encoder;
    }

    public void createUser(TaskUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")//Only admins can make this request
    public List<UserResponseDTO> getAllUsers(){
        return repository.findAll().stream().map(UserResponseDTO::new).toList();
    }

    public UserResponseDTO getUser(TaskUserDetail userDetail){
       return new UserResponseDTO(userDetail.getUser());
    }

    public void updateUser(UserRequestDTO updatedUser, TaskUser authenticatedUser) throws CredentialChangedException {
        if(updatedUser.getUsername()!= null){
            authenticatedUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getPassword() != null){
            authenticatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if(updatedUser.getEmail() != null){
            authenticatedUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getRoles() != null) {
            authenticatedUser.setRole(updatedUser.getRoles());
            System.out.println(updatedUser.getRoles());
        }
        repository.save(authenticatedUser);
        throw new CredentialChangedException("Credential has been changed");
    }

    public void deleteUser(TaskUser user) {
        repository.delete(user);
    }
}
