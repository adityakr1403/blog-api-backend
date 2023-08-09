package com.aditya.blog.post;

import com.aditya.blog.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Optional<Post> findBySlugAndCreatedAtBetweenOrderByCreatedAtDesc(String slug, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
