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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long authorId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();
}