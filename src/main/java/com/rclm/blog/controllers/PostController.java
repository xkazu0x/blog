package com.rclm.blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rclm.blog.domain.dtos.PostDto;
import com.rclm.blog.domain.entities.Post;
import com.rclm.blog.mappers.PostMapper;
import com.rclm.blog.services.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;
  private final PostMapper  postMapper;

  @GetMapping
  public ResponseEntity<List<PostDto>> getPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {
    List<Post>    posts    = postService.getPosts(categoryId, tagId);
    List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
    return(ResponseEntity.ok(postDtos));
  }
}
