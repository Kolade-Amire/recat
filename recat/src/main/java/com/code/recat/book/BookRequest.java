package com.code.recat.book;

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
    private String authorName;
    private String blurb;
    private Integer publicationYear;
    private Set<Genre> genres;
    private String isbn;
    private String coverImageUrl;
}
