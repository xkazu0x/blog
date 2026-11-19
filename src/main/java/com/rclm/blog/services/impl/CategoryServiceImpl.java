package com.rclm.blog.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rclm.blog.domain.entities.Category;
import com.rclm.blog.repositories.CategoryRepository;
import com.rclm.blog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> listCategories() {
    return(categoryRepository.findAllWithPostCount());
  }
}
