package com.rclm.blog.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rclm.blog.domain.dtos.CategoryDto;
import com.rclm.blog.domain.dtos.CreateCategoryRequest;
import com.rclm.blog.domain.entities.Category;
import com.rclm.blog.mappers.CategoryMapper;
import com.rclm.blog.services.CategoryService;

import jakarta.validation.Valid;
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

  @PostMapping
  public ResponseEntity<CategoryDto>
  createCategory(@Valid @RequestBody CreateCategoryRequest req) {
    Category e = categoryMapper.toEntity(req);
    Category c = categoryService.createCategory(e);
    return(new ResponseEntity<>(categoryMapper.toDto(c), HttpStatus.CREATED));
  }
}
