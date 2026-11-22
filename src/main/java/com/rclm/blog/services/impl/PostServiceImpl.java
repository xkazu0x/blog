package com.rclm.blog.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rclm.blog.domain.PostStatus;
import com.rclm.blog.domain.entities.Category;
import com.rclm.blog.domain.entities.Post;
import com.rclm.blog.domain.entities.Tag;
import com.rclm.blog.domain.entities.User;
import com.rclm.blog.repositories.PostRepository;
import com.rclm.blog.services.CategoryService;
import com.rclm.blog.services.PostService;
import com.rclm.blog.services.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository  postRepository;
  private final CategoryService categoryService;
  private final TagService      tagService;

  @Override
  @Transactional(readOnly = true)
  public List<Post> getPublishedPostsMaybeByCategoryAndOrTag(UUID categoryId, UUID tagId) {
    if (categoryId != null && tagId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      Tag tag = tagService.getTagById(tagId);
      return(postRepository.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag));
    }
    if (categoryId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      return(postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category));
    }
    if (tagId != null) {
      Tag tag = tagService.getTagById(tagId);
      return(postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag));
    }
    return(postRepository.findAllByStatus(PostStatus.PUBLISHED));
  }

  @Override
  public List<Post> getUserDraftedPosts(User user) {
    return(postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT));
  }
}
