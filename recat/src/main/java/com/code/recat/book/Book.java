package com.code.recat.book;

import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.net.URL;

@Table(name = "books")
public record Book(
        @Id
        int book_id,
        String title,
        int author_id,
        String description_text,
        int publication_year,
        int genre_id,
        String isbn,
        URL cover_image_url
) {}

