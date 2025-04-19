package com.Task.Manager.configuration;

import com.Task.Manager.service.JwtService;
import com.Task.Manager.service.TaskUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private TaskUserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity httpSecurity) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{
       JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authenticationManager(http), jwtService);
       JwtValidationFilter jwtValidationFilter= new JwtValidationFilter(jwtService, userDetailsService);
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth", "/api/sign-up").permitAll();
                    auth.anyRequest().authenticated();
                })
                .anonymous(AnonymousConfigurer::disable)
                .exceptionHandling(handler-> handler.authenticationEntryPoint(entryPoint))
                .addFilter(jwtAuthFilter)
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class); // this might be the source of my challenge

        return http.build();
    }
}
