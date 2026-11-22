package com.rclm.blog.services;

import java.util.List;

import com.rclm.blog.domain.entities.Tag;

public interface TagService {
  List<Tag> getTags();
}
