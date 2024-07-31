package com.code.recat.author;

import com.code.recat.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(name = "author_seq", sequenceName = "public.authors_author_id_seq", allocationSize = 1)
    private Long authorId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;
}