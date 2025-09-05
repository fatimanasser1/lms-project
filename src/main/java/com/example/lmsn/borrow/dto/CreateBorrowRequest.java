package com.example.lmsn.borrow.dto;

import jakarta.validation.constraints.NotNull;


public record CreateBorrowRequest(
        @NotNull Long bookId
) {}
