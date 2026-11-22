package com.rclm.blog.services;

import java.util.UUID;

import com.rclm.blog.domain.entities.User;

public interface UserService {
  User getUserById(UUID id);
}
