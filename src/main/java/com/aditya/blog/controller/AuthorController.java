package com.aditya.blog.controller;


import com.aditya.blog.author.AuthorDTO;
import com.aditya.blog.author.AuthorListResponse;
import com.aditya.blog.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<AuthorListResponse> getAllAuthors(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "page-size", defaultValue = "10") Integer pageSize,
                                                            @RequestParam(name = "sort-dir", defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(authorService.getAllAuthors( page, pageSize, sortDir));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AuthorDTO> getAuthorByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(authorService.getAuthorByUsername(username));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthorDTO> registerAuthor(@RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.registerAuthor(authorDTO));
    }
}
