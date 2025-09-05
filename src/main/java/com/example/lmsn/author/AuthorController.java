package com.example.lmsn.author;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService service;
    public AuthorController(AuthorService service) { this.service = service; }

    @GetMapping
    public List<Author> list() { return service.list(); }

    @PostMapping
    public Author create(@RequestBody Author a) { return service.create(a); }
}
