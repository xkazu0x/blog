package com.rclm.blog.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.rclm.blog.domain.PostStatus;
import com.rclm.blog.domain.dtos.TagDto;
import com.rclm.blog.domain.entities.Post;
import com.rclm.blog.domain.entities.Tag;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
  TagDto toTagDto(Tag tag);

  @Named("calculatePostCount")
  default Integer calculatePostCount(Set<Post> posts) {
    if (posts == null) return(0);
    return((int)posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count());
  }
}
