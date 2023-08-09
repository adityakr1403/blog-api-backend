package com.aditya.blog.post;

import com.aditya.blog.post.PostDTO;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PostDetailResponse {
    PostDTO post;
    String message;
    Boolean found;
}
