package com.rclm.blog.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rclm.blog.domain.entities.Tag;
import com.rclm.blog.repositories.TagRepository;
import com.rclm.blog.services.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository tagRepository;

  @Override
  public List<Tag> getTags() {
    List<Tag> tags = tagRepository.findAllWithPostCount();
    return(tags);
  }
}
