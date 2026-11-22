package com.rclm.blog.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rclm.blog.domain.entities.Tag;
import com.rclm.blog.repositories.TagRepository;
import com.rclm.blog.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

  @Override
  @Transactional
  public List<Tag> createTags(Set<String> tagNames) {
    List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

    Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

    List<Tag> newTags = tagNames.stream()
      .filter(name -> !existingTagNames.contains(name))
      .map(name -> Tag.builder()
        .name(name)
        .posts(new HashSet<>())
        .build())
      .collect(Collectors.toList());

    List<Tag> savedTags = new ArrayList<>();
    if (!newTags.isEmpty()) {
      savedTags = tagRepository.saveAll(newTags);
    }
    savedTags.addAll(existingTags);

    return(savedTags);
  }

  @Override
  @Transactional
  public void deleteTag(UUID id) {
    tagRepository.findById(id).ifPresent(tag -> {
      if (!tag.getPosts().isEmpty()) {
        throw new IllegalStateException("Cannot delete tag with posts");
      }
      tagRepository.deleteById(id);
    });
  }

  @Override
  public Tag getTagById(UUID id) {
    return(tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id)));
  }
}
