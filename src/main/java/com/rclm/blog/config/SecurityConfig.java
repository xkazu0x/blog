package com.rclm.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rclm.blog.domain.entities.User;
import com.rclm.blog.repositories.UserRepository;
import com.rclm.blog.security.BlogUserDetailsService;
import com.rclm.blog.security.JwtAuthFilter;
import com.rclm.blog.services.AuthService;

@Configuration
public class SecurityConfig {
  @Bean
  public JwtAuthFilter jwtAuthFilter(AuthService authService) {
    return(new JwtAuthFilter(authService));
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    BlogUserDetailsService blogUserDetailsService = new BlogUserDetailsService(userRepository);
    String email = "user@test.com";
    userRepository.findByEmail(email).orElseGet(() -> {
      User newUser = User.builder()
        .name("name")
        .email(email)
        .password(passwordEncoder().encode("password"))
        .build();
      return(userRepository.save(newUser));
    });
    return(blogUserDetailsService);
  }

  @Bean
  public SecurityFilterChain
  securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
    http.authorizeHttpRequests(auth -> auth
      .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
      .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
      .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
      .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
      .anyRequest().authenticated()
    )
    .csrf(csrf -> csrf.disable())
    .sessionManagement(session -> session
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    )
    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return(http.build());
  }

  @Bean
  public PasswordEncoder
  passwordEncoder() {
    return(PasswordEncoderFactories.createDelegatingPasswordEncoder());
  }

  @Bean
  public AuthenticationManager
  authenticationManager(AuthenticationConfiguration config) throws Exception {
    return(config.getAuthenticationManager());
  }
}
