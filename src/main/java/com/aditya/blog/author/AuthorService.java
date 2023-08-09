package com.aditya.blog.author;


import com.aditya.blog.author.AuthorDTO;
import com.aditya.blog.author.AuthorListResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

public interface AuthorService {
    AuthorDTO getAuthorByUsername(String username);

    AuthorDTO registerAuthor(AuthorDTO authorDTO);


    AuthorListResponse getAllAuthors(Integer page, Integer pageSize, String sortDir);
}
