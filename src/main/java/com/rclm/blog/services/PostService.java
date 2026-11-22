package com.rclm.blog.services;

import java.util.List;
import java.util.UUID;

import com.rclm.blog.domain.entities.Post;

public interface PostService {
  List<Post> getPosts(UUID categoryId, UUID tagId);
}
