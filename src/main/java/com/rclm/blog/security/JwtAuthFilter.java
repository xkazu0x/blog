package com.rclm.blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rclm.blog.services.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
  private final AuthService authService;

  @Override
  protected void
  doFilterInternal(HttpServletRequest request,
                   HttpServletResponse response,
                   FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = extractToken(request);
      if (token != null) {
        UserDetails userDetails = authService.validateToken(token);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        if (userDetails instanceof BlogUserDetails) {
          request.setAttribute("userId", ((BlogUserDetails)userDetails).getId());
        }
      }
    } catch (Exception e) {
      // NOTE: do not throw exceptions, just don't authenticate the user
      log.warn("Received invalid auth token.");
    }
    filterChain.doFilter(request, response);
  }

  private String
  extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return(bearerToken.substring(7));
    }
    return(null);
  }
}
