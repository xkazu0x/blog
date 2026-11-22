package com.rclm.blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rclm.blog.domain.dtos.PostDto;
import com.rclm.blog.domain.entities.Post;
import com.rclm.blog.domain.entities.User;
import com.rclm.blog.mappers.PostMapper;
import com.rclm.blog.services.PostService;
import com.rclm.blog.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  private final UserService userService;
  private final PostMapper  postMapper;

  @GetMapping
  public ResponseEntity<List<PostDto>> getPublishedPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {
    List<Post>    posts    = postService.getPublishedPostsMaybeByCategoryAndOrTag(categoryId, tagId);
    List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
    return(ResponseEntity.ok(postDtos));
  }

  @GetMapping(path = "/drafts")
  public ResponseEntity<List<PostDto>> getUserDraftedPosts(@RequestAttribute UUID userId) {
    User user = userService.getUserById(userId);
    List<Post>    draftedPosts    = postService.getUserDraftedPosts(user);
    List<PostDto> draftedPostDtos = draftedPosts.stream().map(postMapper::toDto).toList();
    return(ResponseEntity.ok(draftedPostDtos));
  }
}
