package com.code.recat.book;

import com.code.recat.author.AuthorDto;
import com.code.recat.comment.CommentDto;
import com.code.recat.genre.GenreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
public class BookViewDto {
    private Integer id;
    private String title;
    private AuthorDto author;
    private String blurb;
    private Integer publicationYear;
    private Set<GenreDto> genres;
    private String isbn;
    private String coverImageUrl;
    private Set<CommentDto> comments;
}
