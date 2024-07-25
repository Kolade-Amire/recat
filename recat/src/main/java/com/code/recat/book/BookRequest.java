package com.code.recat.book;

import com.code.recat.author.Author;
import com.code.recat.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookRequest {
    private String title;
    private Author author;
    private String blurb;
    private Integer publication_year;
    private Set<Genre> genres;
    private String isbn;
    private String cover_image_url;
}
