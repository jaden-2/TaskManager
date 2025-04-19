package com.Task.Manager.configuration;

import com.Task.Manager.DTO.LoginDTO;
import com.Task.Manager.entity.TaskUserDetail;
import com.Task.Manager.service.JwtService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authManager, JwtService service){
        this.authenticationManager = authManager;
        this.jwtService = service;
        this.setFilterProcessesUrl("/api/auth");
    }
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            LoginDTO loginDTO = mapper.readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

            return  authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        TaskUserDetail userDetails = (TaskUserDetail) authResult.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        response.setContentType("application/json");
        response.getWriter().write("{\n" + " \"token\": " + "\""+token+ "\"}");
        response.getWriter().flush();
    }
}
