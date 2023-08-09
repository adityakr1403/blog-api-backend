package com.aditya.blog.author;

import com.aditya.blog.author.AuthorDTO;
import com.aditya.blog.author.Author;
import com.aditya.blog.author.AuthorRepository;
import com.aditya.blog.author.AuthorListResponse;
import com.aditya.blog.author.AuthorService;
import com.aditya.blog.exception.ResourceAlreadyExist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthorListResponse getAllAuthors(Integer page, Integer pageSize, String sortDir) {
        String sortBy = "username";
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Author> pageContent = authorRepository.findAll(pageable);
        List<Author> authors = pageContent.getContent();
        System.out.println("Authors: " + authors.toString());
        List<AuthorDTO> authorDTOS = AuthorDTO.fromEntities(authors);
        return AuthorListResponse.builder()
                .authors(authorDTOS)
                .page(pageContent.getNumber())
                .pageSize(pageContent.getSize())
                .totalPages(pageContent.getTotalPages())
                .totalAuthors(pageContent.getTotalElements())
                .isLast(pageContent.isLast())
                .build();
    }

    @Override
    public AuthorDTO getAuthorByUsername(String username) {
        Author author = authorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Author with username " + username + " not found"));
        System.out.println(author);
        return AuthorDTO.fromAuthor(author);
    }

    @Override
    public AuthorDTO registerAuthor(AuthorDTO authorDTO) {
        System.out.println("AuthorDTO: " + authorDTO);
        boolean exist = authorRepository.findByUsername(authorDTO.getUsername())
                .isPresent();
        if (exist) {
            throw new ResourceAlreadyExist("Author with username " + authorDTO.getUsername() + " already exists");
        }
        Author author = Author.builder()
                .username(authorDTO.getUsername())
                .email(authorDTO.getEmail())
                .password(passwordEncoder.encode(authorDTO.getPassword()))
                .authority(Authorities.AUTHOR)
                .build();
        authorRepository.save(author);
        return AuthorDTO.fromAuthor(author);
    }
}
