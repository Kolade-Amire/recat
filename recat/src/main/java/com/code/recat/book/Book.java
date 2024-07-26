package com.code.recat.book;

import com.code.recat.author.Author;
import com.code.recat.book.comment.Comment;
import com.code.recat.genre.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Data
@Entity
public class Book{
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq")
        @Column(nullable = false, updatable = false)
        @SequenceGenerator(name = "books_seq", sequenceName = "public.books_seq", allocationSize = 1)
        private Long bookId;
        private String title;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "author_id", nullable = false)
        private Author author;

        private String blurb;
        private Integer publicationYear;

        @ManyToMany(fetch = FetchType.LAZY)
        @Builder.Default
        @JoinTable(
                name = "book_genre",
                joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_id")
        )
        private Set<Genre> genres = new HashSet<>();

        private String isbn;
        private String coverImageUrl;

        @Builder.Default
        @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Comment> comments = new HashSet<>();
}

