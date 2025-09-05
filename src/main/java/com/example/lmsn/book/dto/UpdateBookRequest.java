package com.example.lmsn.book.dto;

public record UpdateBookRequest(
        String title,
        String isbn,
        Integer publicationYear,
        Integer totalCopies,
        Integer availableCopies,
        Long authorId
) {}
