package com.code.recat.book;

import com.code.recat.author.AuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BookDtoMapper {


    // To handle the different format book object is to be returned
    public static <C extends Collection<BookDto>> C mapAnyCollectionToDto(Collection<Book> books, Supplier<C> collectionFactory){
        return books.stream()
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
                .collect(Collectors.toCollection(collectionFactory));

    }

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
        var bookDtos = mapAnyCollectionToDto(books.getContent(), ArrayList::new);

        return new PageImpl<>(bookDtos, PageRequest.of(books.getNumber(), books.getSize(), books.getSort()), books.getTotalElements());

    }

    public static Set<BookDto> mapBookSetToDto(Set<Book> books) {
        var bookDtos =  mapAnyCollectionToDto(books, HashSet::new);
        return new HashSet<>(bookDtos);
    }
}
