package com.example.lmsn.borrow;

import com.example.lmsn.book.Book;
import com.example.lmsn.book.BookRepository;
import com.example.lmsn.borrow.dto.BorrowResponse;
import com.example.lmsn.borrow.dto.CreateBorrowRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepo;
    private final BookRepository bookRepo;
    private static final int MAX_ACTIVE_BORROWS = 5;
    private static final int LOAN_DAYS = 14;

    public BorrowService(BorrowRepository borrowRepo, BookRepository bookRepo) {
        this.borrowRepo = borrowRepo;
        this.bookRepo = bookRepo;
    }

    public List<BorrowResponse> myBorrows(String userId) {
        return borrowRepo.findByUserIdOrderByBorrowedAtDesc(userId).stream()
                .map(this::toDto).toList();
    }

    @Transactional
    public BorrowResponse borrowBook(String userId, CreateBorrowRequest req) {
        Long bookId = req.bookId();

        // business validation
        long active = borrowRepo.countByUserIdAndStatus(userId, BorrowStatus.BORROWED);
        if (active >= MAX_ACTIVE_BORROWS) {
            throw new IllegalStateException("Max active borrows reached: " + MAX_ACTIVE_BORROWS);
        }

        if (borrowRepo.existsByUserIdAndBookIdAndStatus(userId, bookId, BorrowStatus.BORROWED)) {
            throw new IllegalStateException("You already have an active borrow for this book");
        }

        Book book = bookRepo.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime due = now.plusDays(LOAN_DAYS);

        BorrowRecord r = BorrowRecord.builder()
                .userId(userId)
                .book(book)
                .borrowedAt(now)
                .dueAt(due)
                .status(BorrowStatus.BORROWED)
                .renewalCount(0)
                .build();

        BorrowRecord saved = borrowRepo.save(r);
        return toDto(saved);
    }

    @Transactional
    public BorrowResponse returnBook(String userId, Long borrowId) {
        BorrowRecord r = borrowRepo.findById(borrowId).orElseThrow(() -> new IllegalArgumentException("Borrow not found"));
        if (!r.getUserId().equals(userId)) {
            throw new SecurityException("Not your borrow record");
        }
        if (r.getStatus() != BorrowStatus.BORROWED) {
            throw new IllegalStateException("Borrow is not in BORROWED state");
        }

        r.setReturnedAt(OffsetDateTime.now(ZoneOffset.UTC));
        r.setStatus(BorrowStatus.RETURNED);
        borrowRepo.save(r);

        Book book = r.getBook();
        if (book.getAvailableCopies() == null) book.setAvailableCopies(0);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        return toDto(r);
    }

    private BorrowResponse toDto(BorrowRecord r) {
        return new BorrowResponse(
                r.getId(),
                r.getBook().getId(),
                r.getBook().getTitle(),
                r.getUserId(),
                r.getBorrowedAt(),
                r.getDueAt(),
                r.getReturnedAt(),
                r.getStatus().name(),
                r.getRenewalCount()
        );
    }
}
