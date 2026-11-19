package com.rclm.blog.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rclm.blog.domain.dtos.CategoryDto;
import com.rclm.blog.mappers.CategoryMapper;
import com.rclm.blog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> listCategories() {
    List<CategoryDto> categories = categoryService.listCategories().stream().map(categoryMapper::toDto).toList();
    return(ResponseEntity.ok(categories));
  }
}
