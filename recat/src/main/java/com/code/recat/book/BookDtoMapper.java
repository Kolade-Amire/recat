package com.code.recat.book;

import com.code.recat.author.AuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.Set;

public class BookDtoMapper {

    public static BookDto mapBookToDto (Book book){
        var authorDto = new AuthorDto(book.getAuthor().getAuthorId(), book.getAuthor().getName());

        return BookDto.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(authorDto)
                .blurb(book.getBlurb())
                .publicationYear(book.getPublicationYear())
                .genres(book.getGenres())
                .isbn(book.getIsbn())
                .coverImageUrl(book.getCoverImageUrl())
                .comments(book.getComments())
                .build();
    }

    public static Page<BookDto> mapBookPageToDto(Page<Book> books){
        var bookDtos =  books.stream()
                .map( book -> BookDto.builder()
                        .bookId(book.getBookId())
                        .title(book.getTitle())
                        .author(new AuthorDto(book.getAuthor().getAuthorId(), book.getAuthor().getName()))
                        .blurb(book.getBlurb())
                        .publicationYear(book.getPublicationYear())
                        .genres(book.getGenres())
                        .isbn(book.getIsbn())
                        .coverImageUrl(book.getCoverImageUrl())
                        .comments(book.getComments())
                        .build())
                .toList();


        return new PageImpl<>(bookDtos, PageRequest.of(books.getNumber(), books.getSize(), books.getSort()), books.getTotalElements());

    }

    //TODO: create a function that uses generic types
    public static Set<BookDto> mapBookSetToDto(Set<Book> books) {
        var bookDtos =  books.stream()
                .map( book -> BookDto.builder()
                        .bookId(book.getBookId())
                        .title(book.getTitle())
                        .author(new AuthorDto(book.getAuthor().getAuthorId(), book.getAuthor().getName()))
                        .blurb(book.getBlurb())
                        .publicationYear(book.getPublicationYear())
                        .genres(book.getGenres())
                        .isbn(book.getIsbn())
                        .coverImageUrl(book.getCoverImageUrl())
                        .comments(book.getComments())
                        .build())
                .toList();
        return new HashSet<>(bookDtos);
    }
}
