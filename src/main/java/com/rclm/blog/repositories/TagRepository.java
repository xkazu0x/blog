package com.rclm.blog.repositories;

import java.util.UUID;

import com.rclm.blog.domain.entities.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
}
