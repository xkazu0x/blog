package com.rclm.blog.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.rclm.blog.domain.PostStatus;
import com.rclm.blog.domain.dtos.CategoryDto;
import com.rclm.blog.domain.dtos.CreateCategoryRequest;
import com.rclm.blog.domain.entities.Category;
import com.rclm.blog.domain.entities.Post;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
  CategoryDto toDto(Category category);

  Category toEntity(CreateCategoryRequest req);

  @Named("calculatePostCount")
  default long calculatePostCount(List<Post> posts) {
    long result = 0;
    if (posts != null) {
      result = posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
    return(result);
  }
}
