package com.aditya.blog.controller;


import com.aditya.blog.post.PostDTO;
import com.aditya.blog.post.PostDetailResponse;
import com.aditya.blog.post.PostListResponse;
import com.aditya.blog.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/blog/posts")
@RequiredArgsConstructor
public class BlogController {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<PostListResponse> getPostsByPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(name = "page-size", defaultValue = "10") Integer pageSize,
                                                           @RequestParam(name = "sort-dir", defaultValue = "desc") String sortDir,
                                                           @RequestParam(name = "from", defaultValue = "2023-01-01") LocalDate from,
                                                           @RequestParam(name = "to", defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate to) {
        System.out.println("this is all posts");
        return ResponseEntity.ok(postService.getPostsByPage(page, pageSize, sortDir, from.atStartOfDay(), to.plusDays(1).atStartOfDay()));
    }

    @GetMapping("/{date}/{slug}")
    public ResponseEntity<PostDetailResponse> getPostBySlug(@PathVariable("date") LocalDate date,
                                                            @PathVariable("slug") String slug) throws Exception {
        return ResponseEntity.ok(postService.getPostBySlug(slug, date.atStartOfDay(), date.plusDays(1).atStartOfDay()));
    }

    @PostMapping("/create")
    public ResponseEntity<PostDetailResponse> createPost(@RequestBody PostDTO postDTO, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(postService.createPost(postDTO, username));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostDetailResponse> updatePost(@PathVariable("id") Long id,
                                                         @RequestBody PostDTO postDTO, Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(postService.updatePost(id, postDTO, username));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id, Authentication authentication) {
        String username = authentication.getName();
        postService.deletePostById(id, username);
        return ResponseEntity.ok("Post deleted successfully");
    }

}


