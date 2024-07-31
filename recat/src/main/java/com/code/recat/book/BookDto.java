package com.code.recat.book;

import com.code.recat.author.AuthorDto;
import com.code.recat.book.comment.Comment;
import com.code.recat.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
public class BookDto {
    private Long bookId;
    private String title;
    private AuthorDto author;
    private String blurb;
    private Integer publicationYear;
    private Set<Genre> genres;
    private String isbn;
    private String coverImageUrl;
    private Set<Comment> comments;
}
