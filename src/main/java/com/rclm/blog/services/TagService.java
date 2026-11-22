package com.rclm.blog.services;

import java.util.List;
import java.util.Set;

import com.rclm.blog.domain.entities.Tag;

public interface TagService {
  List<Tag> getTags();
  List<Tag> createTags(Set<String> tagNames);
  void      deleteTag(UUID id);
}
