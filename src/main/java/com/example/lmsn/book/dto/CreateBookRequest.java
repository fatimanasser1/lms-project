package com.example.lmsn.book.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record CreateBookRequest(
        @NotBlank String title,
        String isbn,
        @Min(1000) @Max(3000) Integer publicationYear,
        @Min(0) Integer totalCopies,
        @Min(0) Integer availableCopies,
        @NotNull Long authorId
) {}