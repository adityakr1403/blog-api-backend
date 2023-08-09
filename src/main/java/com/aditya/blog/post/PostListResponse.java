package com.aditya.blog.post;

import com.aditya.blog.post.PostDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class PostListResponse {
    private List<PostDTO> posts;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalPosts;
    private Boolean isLast;
    private LocalDateTime from;
    private LocalDateTime to;

}
