package com.example.lmsn.borrow.dto;

import java.time.OffsetDateTime;

public record BorrowResponse(
        Long id,
        Long bookId,
        String bookTitle,
        String userId,
        OffsetDateTime borrowedAt,
        OffsetDateTime dueAt,
        OffsetDateTime returnedAt,
        String status,
        Integer renewalCount
) {}
