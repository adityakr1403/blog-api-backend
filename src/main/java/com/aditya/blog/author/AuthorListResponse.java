package com.aditya.blog.author;

import com.aditya.blog.author.AuthorDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class AuthorListResponse {
    private List<AuthorDTO> authors;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalAuthors;
    private Boolean isLast;
}
