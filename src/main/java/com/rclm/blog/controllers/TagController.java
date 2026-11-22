package com.rclm.blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rclm.blog.domain.dtos.CreateTagsRequest;
import com.rclm.blog.domain.dtos.TagDto;
import com.rclm.blog.domain.entities.Tag;
import com.rclm.blog.mappers.TagMapper;
import com.rclm.blog.services.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;
  private final TagMapper  tagMapper;

  @GetMapping
  public ResponseEntity<List<TagDto>> getTags() {
    List<Tag> tags = tagService.getTags();
    List<TagDto> tagResponses = tags.stream().map(tagMapper::toTagDto).toList();
    return(ResponseEntity.ok(tagResponses));
  }

  @PostMapping
  public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
    List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
    List<TagDto> createdTagResponses = savedTags.stream().map(tagMapper::toTagDto).toList();
    return(new ResponseEntity<>(createdTagResponses, HttpStatus.CREATED));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
    tagService.deleteTag(id);
    return(ResponseEntity.noContent().build());
  }
}
