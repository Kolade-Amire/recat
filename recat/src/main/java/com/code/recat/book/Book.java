package com.code.recat.book;

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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false, updatable = false)
        private Integer book_id;
        private String title;
        private Integer author_id;
        private String blurb;
        private Integer publication_year;
        @ElementCollection(targetClass = Integer.class)
        private Set<Integer> genres = new HashSet<>();
        private String isbn;
        private String cover_image_url;
}

