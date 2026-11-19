package com.rclm.blog.repositories;

import java.util.UUID;

import com.rclm.blog.domain.entities.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
