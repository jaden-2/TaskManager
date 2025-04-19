package com.Task.Manager.service;

import com.Task.Manager.entity.TaskUser;
import com.Task.Manager.entity.TaskUserDetail;
import com.Task.Manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public TaskUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        TaskUser taskUser = repository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        return new TaskUserDetail(taskUser);
    }
}
