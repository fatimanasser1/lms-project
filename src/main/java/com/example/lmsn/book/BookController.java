package com.example.lmsn.book;

import com.example.lmsn.book.dto.BookResponse;
import com.example.lmsn.book.dto.CreateBookRequest;
import com.example.lmsn.book.dto.UpdateBookRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service) { this.service = service; }

    @GetMapping
    public List<BookResponse> list() {
        return service.list(); }

    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Long id) {
        return service.get(id); }

    @PostMapping
    public BookResponse create(@Valid @RequestBody CreateBookRequest req) {
        return service.create(req); }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @RequestBody UpdateBookRequest req) {
        return service.update(id, req); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
