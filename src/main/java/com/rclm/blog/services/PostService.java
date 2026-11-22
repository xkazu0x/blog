package com.rclm.blog.services;

import java.util.List;
import java.util.UUID;

import com.rclm.blog.domain.CreatePostRequest;
import com.rclm.blog.domain.entities.Post;
import com.rclm.blog.domain.entities.User;

public interface PostService {
  List<Post> getPublishedPostsMaybeByCategoryAndOrTag(UUID categoryId, UUID tagId);
  List<Post> getUserDraftedPosts(User user);
  Post       createUserPost(User user, CreatePostRequest createPostRequest);
}
