package com.code.recat.book;


import com.code.recat.author.Author;
import com.code.recat.author.AuthorService;
import com.code.recat.genre.Genre;
import com.code.recat.genre.GenreService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;


    @Override
    public Page<BookViewDto> findAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var book = bookRepository.findAllByOrderByTitle(pageable);
        return BookDtoMapper.mapBookPageToDto(book);
    }

    @Override
    @Transactional
    public Book addNewBook(BookRequest bookRequest) {

        var author = authorService.getAuthorByName(bookRequest.getAuthorName());

        Set<Genre> managedGenres = bookRequest.getGenres().stream().map(
                genre -> genreService.getGenreById(genre.getGenreId())
        ).collect(Collectors.toSet());

        var newBook = Book.builder()
                .title(bookRequest.getTitle())
                .author(author)
                .blurb(bookRequest.getBlurb())
                .publicationYear(bookRequest.getPublicationYear())
                .genres(managedGenres)
                .isbn(bookRequest.getIsbn())
                .coverImageUrl(bookRequest.getCoverImageUrl())
                .build();

        return bookRepository.save(newBook);
    }

    @Override
    public Page<BookViewDto> findMatchingBooksByTitleOrAuthorName(String searchQuery, int pageNumber, int pageSize) {
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

        if (bookRequest.getTitle() != null) {
            existingBook.setTitle(bookRequest.getTitle());
        }
        if (bookRequest.getBlurb() != null) {
            existingBook.setBlurb(bookRequest.getBlurb());
        }
        if (bookRequest.getPublicationYear() != null) {
            existingBook.setPublicationYear(bookRequest.getPublicationYear());
        }
        if (bookRequest.getGenres() != null) {
            existingBook.setGenres(new HashSet<>(bookRequest.getGenres()));
        }
        if (bookRequest.getIsbn() != null) {
            existingBook.setIsbn(bookRequest.getIsbn());
        }
        if (bookRequest.getCoverImageUrl() != null) {
            existingBook.setCoverImageUrl(bookRequest.getCoverImageUrl());
        }

        return bookRepository.save(existingBook);
    }


    @Override
    @Transactional
    public BookViewDto findBookForView(Long id) {
        System.out.println("Fetching book with ID: " + id);
        var book = bookRepository.findBookForView(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Book does not exist")
                );

        System.out.println("Genres: " + book.getGenres());
        return BookDtoMapper.mapBookToDto(book);
    }

    @Override
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book does not exist"));
    }

//    @Override
//    public Set<Book> addBookToAuthorProfile(Author author, Long bookId) {
//
//        var existingAuthorBooks = author.getBooks();
//
//        var newBook = findById(bookId);
//        existingAuthorBooks.add(newBook);
//
//        author.setBooks(existingAuthorBooks);
//        authorService.saveAuthor(author);
//
//        return existingAuthorBooks;
//    }

}
