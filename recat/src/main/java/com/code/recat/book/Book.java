package com.code.recat.book;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.net.URL;

@Table("books")
public record Book(
        @Id
        int book_id,
        @NotBlank
        String title,
        int author_id,
        String description_text,
        int publication_year,
        int category_id,
        String isbn,
        URL cover_image_url
) {}

