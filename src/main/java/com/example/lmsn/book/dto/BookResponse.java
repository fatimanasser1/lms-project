package com.example.lmsn.book.dto;

public record BookResponse(
        Long id,
        String title,
        String isbn,
        Integer publicationYear,
        Integer totalCopies,
        Integer availableCopies,
        Long authorId,
        String authorName
) {}