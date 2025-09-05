package com.example.lmsn.book;

import com.example.lmsn.author.AuthorService;
import com.example.lmsn.book.dto.BookResponse;
import com.example.lmsn.book.dto.CreateBookRequest;
import com.example.lmsn.book.dto.UpdateBookRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepository repo;
    private final AuthorService authors;

    public BookService(BookRepository repo, AuthorService authors) {
        this.repo = repo; this.authors = authors;
    }

        public List<BookResponse> list() {

        return repo.findAllWithAuthor().stream().map(this::toDto).toList();
    }

    public BookResponse get(Long id) {
        return toDto(repo.findById(id).orElseThrow()); }

    @Transactional
    public BookResponse create(CreateBookRequest r) {
        var author = authors.get(r.authorId());
        var b = Book.builder()
                .title(r.title())
                .isbn(r.isbn())
                .publicationYear(r.publicationYear())
                .totalCopies(r.totalCopies())
                .availableCopies(r.availableCopies())
                .author(author)
                .build();
        return toDto(repo.save(b));
    }
    @Transactional
    public BookResponse update(Long id, UpdateBookRequest r) {
        var b = repo.findById(id).orElseThrow();
        if (r.title()!=null) b.setTitle(r.title());
        if (r.isbn()!=null) b.setIsbn(r.isbn());
        if (r.publicationYear()!=null) b.setPublicationYear(r.publicationYear());
        if (r.totalCopies()!=null) b.setTotalCopies(r.totalCopies());
        if (r.availableCopies()!=null) b.setAvailableCopies(r.availableCopies());
        if (r.authorId()!=null) b.setAuthor(authors.get(r.authorId()));
        return toDto(b);
    }

    public void delete(Long id) {
        repo.deleteById(id); }

    private BookResponse toDto(Book b) {
        return new BookResponse(
                b.getId(), b.getTitle(), b.getIsbn(), b.getPublicationYear(),
                b.getTotalCopies(), b.getAvailableCopies(),
                b.getAuthor().getId(), b.getAuthor().getName()
        );
    }
}
