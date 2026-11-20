package com.rclm.blog.services;

import java.util.List;

import com.rclm.blog.domain.entities.Category;

public interface CategoryService {
  List<Category> listCategories();
  Category createCategory(Category category);
}
