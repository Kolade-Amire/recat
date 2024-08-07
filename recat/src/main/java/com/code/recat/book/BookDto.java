package com.code.recat.book;

import com.code.recat.author.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class BookDto {
    private Long bookId;
    private String title;
    private AuthorDto author;
    private String blurb;
    private Integer publicationYear;
    private String isbn;
    private String coverImageUrl;
}
