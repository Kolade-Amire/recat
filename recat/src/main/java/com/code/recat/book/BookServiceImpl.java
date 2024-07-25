package com.code.recat.book;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Override
    public Page<Book> findAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllByOrderByTitle(pageable);
    }

    @Override
    @Transactional
    public Book addNewBook(BookRequest bookRequest) {

        var newBook = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .blurb(bookRequest.getBlurb())
                .publicationYear(bookRequest.getPublication_year())
                .genres(bookRequest.getGenres())
                .isbn(bookRequest.getIsbn())
                .coverImageUrl(bookRequest.getCover_image_url())
                .build();

        return bookRepository.save(newBook);
    }

    @Override
    public Page<Book> findMatchingBooksByTitleOrAuthorName(String searchQuery, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookRepository.searchBooksByTitleOrAuthorName(searchQuery, pageable);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        var book = findBookById(bookId);
        book.getGenres().clear();
        bookRepository.delete(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long bookId, BookRequest bookRequest) {

        Book existingBook = findBookById(bookId);
        if (bookRequest.getTitle() != null){ existingBook.setTitle(bookRequest.getTitle());}
        if (bookRequest.getBlurb() != null){ existingBook.setBlurb(bookRequest.getBlurb());}
        if (bookRequest.getPublication_year() != null){ existingBook.setPublicationYear(bookRequest.getPublication_year());}
        if (bookRequest.getGenres() != null){ existingBook.setGenres(bookRequest.getGenres());}
        if (bookRequest.getIsbn() != null){ existingBook.setIsbn(bookRequest.getIsbn());}
        if (bookRequest.getCover_image_url() != null){ existingBook.setCoverImageUrl(bookRequest.getCover_image_url());}

        return bookRepository.save(existingBook);
    }

    @Override
    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book does not exist"));
    }
}
