package com.aditya.blog.post;

import com.aditya.blog.author.AuthorDTO;
import com.aditya.blog.author.AuthorService;
import com.aditya.blog.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthorService authorService;

    @Override
    public PostListResponse getPostsByPage(Integer page, Integer pageSize, String sortDir, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String sortBy = "updatedAt";
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Post> pageContent = postRepository.findAllByCreatedAtBetween(startDateTime, endDateTime, pageable);
        List<Post> posts = pageContent.getContent();
        List<PostDTO> postDTOS = PostDTO.fromEntities(posts);
        return PostListResponse.builder()
                .posts(postDTOS)
                .page(pageContent.getNumber())
                .pageSize(pageContent.getSize())
                .totalPages(pageContent.getTotalPages())
                .totalPosts(pageContent.getTotalElements())
                .isLast(pageContent.isLast())
                .from(startDateTime)
                .to(endDateTime)
                .build();
    }

    @Override
    public PostDetailResponse createPost(PostDTO postDTO, String username) {
        AuthorDTO author = authorService.getAuthorByUsername(username);
        Post post = PostDTO.toEntity(postDTO);
        post.setAuthor(AuthorDTO.toAuthor(author));
        Post savedPost = postRepository.save(post);
        return PostDetailResponse.builder()
                .post(PostDTO.fromEntity(savedPost))
                .build();
    }

    @Override
    public PostDetailResponse updatePost(Long id, PostDTO postDTO, String username) {
        AuthorDTO author = authorService.getAuthorByUsername(username);
        Post post = postRepository.getReferenceById(id);
        if (!post.getAuthor().getId().equals(author.getId())) {
            throw new RuntimeException("You are not authorized to update this post");
        }
        Post updatedPost = PostDTO.toEntity(postDTO);
        updatedPost.setId(id);
        Post savedPost = postRepository.save(updatedPost);
        return null;
    }

    @Override
    public PostDetailResponse getPostBySlug(String slug, LocalDateTime startDateTime, LocalDateTime endDateTime) throws Exception {

        PostDTO postDTO = PostDTO.fromEntity(
                postRepository
                        .findBySlugAndCreatedAtBetweenOrderByCreatedAtDesc(slug, startDateTime, endDateTime)
                        .orElseThrow(() -> new ResourceNotFoundException("Post not found"))
        );
        return PostDetailResponse.builder()
                .post(postDTO)
                .message("ok")
                .found(true)
                .build();
    }

    @Override
    public PostDTO getPostById(Long id) {
        return PostDTO.fromEntity(postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not found")
        ));
    }

    @Override
    public void deletePostById(Long id, String username) {
        AuthorDTO author = authorService.getAuthorByUsername(username);
        PostDTO postDTO = getPostById(id);
        if (!postDTO.getAuthor().getId().equals(author.getId())) {
            throw new RuntimeException("You are not authorized to delete this post");
        }
        postRepository.deleteById(id);
    }


}
