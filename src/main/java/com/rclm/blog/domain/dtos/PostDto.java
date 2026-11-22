package com.rclm.blog.domain.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.rclm.blog.domain.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
  private UUID          id;
  private String        title;
  private String        content;
  private PostStatus    postStatus;
  private AuthorDto     author;
  private CategoryDto   category;
  private Set<TagDto>   tags;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
