package com.aditya.blog.post;


import com.aditya.blog.author.AuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String slug;
    private AuthorDTO author;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PostStatus status;

    public static List<PostDTO> fromEntities(List<Post> posts) {
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts) {
            postDTOS.add(fromEntity(post));
        }
        return postDTOS;
    }

    public static PostDTO fromEntity(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .author(AuthorDTO.fromAuthor(post.getAuthor()))
                .body(post.getBody())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .status(post.getStatus())
                .build();
    }

    public static Post toEntity(PostDTO postDTO) {
        return Post.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .slug(postDTO.getSlug())
                .author(AuthorDTO.toAuthor(postDTO.getAuthor()))
                .body(postDTO.getBody())
                .createdAt(postDTO.getCreatedAt())
                .updatedAt(postDTO.getUpdatedAt())
                .status(postDTO.getStatus())
                .build();
    }
}
