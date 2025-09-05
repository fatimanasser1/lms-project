package com.example.lmsn.borrow;

import com.example.lmsn.book.Book;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "borrow_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_id", nullable = false, length = 200)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrowed_at", nullable = false)
    private OffsetDateTime borrowedAt;

    @Column(name = "due_at", nullable = false)
    private OffsetDateTime dueAt;

    @Column(name = "returned_at")
    private OffsetDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private BorrowStatus status;

    @Column(name = "renewal_count", nullable = false)
    private Integer renewalCount = 0;

    @Version
    private Integer version;
}
