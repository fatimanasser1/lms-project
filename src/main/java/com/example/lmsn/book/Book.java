package com.example.lmsn.book;

import com.example.lmsn.author.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity @Table(name = "books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 300)
    private String title;

    @Column(length = 40, unique = true)
    private String isbn;

    @Min(1000) @Max(3000)
    private Integer publicationYear;

    @Min(0) private Integer totalCopies;
    @Min(0) private Integer availableCopies;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Version
    private Integer version;

}
