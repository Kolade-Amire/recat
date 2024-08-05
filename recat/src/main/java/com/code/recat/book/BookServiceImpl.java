package com.code.recat.book;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Override
    public Page<BookDto> findAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var book = bookRepository.findAllByOrderByTitle(pageable);
        return BookDtoMapper.mapBookPageToDto(book);
    }

    @Override
    @Transactional
    public Book addNewBook(BookRequest bookRequest) {

        var newBook = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .blurb(bookRequest.getBlurb())
                .publicationYear(bookRequest.getPublicationYear())
                .genres(bookRequest.getGenres())
                .isbn(bookRequest.getIsbn())
                .coverImageUrl(bookRequest.getCoverImageUrl())
                .build();

        return bookRepository.save(newBook);
    }

    @Override
    public Page<BookDto> findMatchingBooksByTitleOrAuthorName(String searchQuery, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var books = bookRepository.searchBooksByTitleOrAuthorName(searchQuery, pageable);
        return BookDtoMapper.mapBookPageToDto(books);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        var book = findById(bookId);
        book.getGenres().clear();
        book.getComments().clear();
        bookRepository.delete(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long bookId, BookRequest bookRequest) {

        var existingBook = findById(bookId);

        if (bookRequest.getTitle() != null){ existingBook.setTitle(bookRequest.getTitle());}
        if (bookRequest.getBlurb() != null){ existingBook.setBlurb(bookRequest.getBlurb());}
        if (bookRequest.getPublicationYear() != null){ existingBook.setPublicationYear(bookRequest.getPublicationYear());}
        if (bookRequest.getGenres() != null){ existingBook.setGenres(new HashSet<>(bookRequest.getGenres()));}
        if (bookRequest.getIsbn() != null){ existingBook.setIsbn(bookRequest.getIsbn());}
        if (bookRequest.getCoverImageUrl() != null){ existingBook.setCoverImageUrl(bookRequest.getCoverImageUrl());}

        return bookRepository.save(existingBook);
    }

    @Override //this method returns a book DTO (for use in the view layer)
    public BookDto findBookById(Long bookId) {
        var book = findById(bookId);
        return BookDtoMapper.mapBookToDto(book);
    }

    @Override //this method is for internal use only (NOT exposed to the view layer)
    public Book findById(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book does not exist"));
    }


}
