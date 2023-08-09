package com.aditya.blog.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Authorities authority;

    public static AuthorDTO fromAuthor(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .username(author.getUsername())
                .email(author.getEmail())
                .password("**********")
                .authority(author.getAuthority())
                .build();
    }

    public static Author toAuthor(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .username(authorDTO.getUsername())
                .email(authorDTO.getEmail())
                .password(authorDTO.getPassword())
                .authority(authorDTO.getAuthority())
                .build();
    }

    public static List<AuthorDTO> fromEntities(List<Author> authors) {
        return authors.stream()
                .map(AuthorDTO::fromAuthor)
                .toList();
    }

}
