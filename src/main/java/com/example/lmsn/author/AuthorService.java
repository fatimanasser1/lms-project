package com.example.lmsn.author;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repo;
    public AuthorService(AuthorRepository repo) { this.repo = repo; }

    public Author get(Long id) { return repo.findById(id).orElseThrow(); }
    public List<Author> list() { return repo.findAll(); }
    public Author create(Author a) { return repo.save(a); }
}
