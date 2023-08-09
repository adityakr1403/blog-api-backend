package com.aditya.blog.post;


import com.aditya.blog.post.PostDTO;
import com.aditya.blog.post.PostListResponse;
import com.aditya.blog.post.PostDetailResponse;

import java.time.LocalDateTime;


public interface PostService {
//    PostListResponse getPostsByPage(Integer page, Integer pageSize, String sortDir);

    PostDetailResponse createPost(PostDTO postDTO, String username);

    PostDTO getPostById(Long id);

    void deletePostById(Long id, String username);

    PostDetailResponse updatePost(Long id, PostDTO postDTO, String username);

    PostDetailResponse getPostBySlug(String slug, LocalDateTime startDateTime, LocalDateTime endDateTime) throws Exception;

    PostListResponse getPostsByPage(Integer page, Integer pageSize, String sortDir, LocalDateTime localDateTime, LocalDateTime localDateTime1);

//    List<PostDTO> getPostsByPage(Integer page);
}
