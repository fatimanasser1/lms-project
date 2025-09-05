package com.example.lmsn.borrow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {
    long countByUserIdAndStatus(String userId, BorrowStatus status);
    boolean existsByUserIdAndBookIdAndStatus(String userId, Long bookId, BorrowStatus status);
    List<BorrowRecord> findByUserIdOrderByBorrowedAtDesc(String userId);
    List<BorrowRecord> findByBookId(Long bookId);
}
