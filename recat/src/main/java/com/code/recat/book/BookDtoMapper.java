package com.code.recat.book;

import com.code.recat.author.AuthorDto;
import com.code.recat.comment.CommentDtoMapper;
import com.code.recat.genre.GenreDtoMapper;
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


    // To handle the different formats book object is to be returned
    public static <C extends Collection<BookViewDto>> C mapAnyCollectionToDto(Collection<Book> books, Supplier<C> collectionFactory){
        return books.stream()
                .map( BookDtoMapper::mapBookToDto)
                .collect(Collectors.toCollection(collectionFactory));

    }

    public static BookViewDto mapBookToDto (Book book){
        var authorDto = new AuthorDto(book.getAuthor().getAuthorId(), book.getAuthor().getName());

        var genreDtos = GenreDtoMapper.mapGenreSetToDto(book.getGenres());

        return BookViewDto.builder()
                .id(book.getBookId())
                .title(book.getTitle())
                .author(authorDto)
                .blurb(book.getBlurb())
                .publicationYear(book.getPublicationYear())
                .genres(genreDtos)
                .isbn(book.getIsbn())
                .coverImageUrl(book.getCoverImageUrl())
                .comments(CommentDtoMapper.mapCommentsToDtos(book.getComments()))
                .build();
    }

    public static Page<BookViewDto> mapBookPageToDto(Page<Book> books){
        var bookDtos = mapAnyCollectionToDto(books.getContent(), ArrayList::new);

        return new PageImpl<>(bookDtos, PageRequest.of(books.getNumber(), books.getSize(), books.getSort()), books.getTotalElements());

    }

    public static Set<BookViewDto> mapBookSetToDto(Set<Book> books) {
        var bookDtos =  mapAnyCollectionToDto(books, HashSet::new);
        return new HashSet<>(bookDtos);
    }

}
