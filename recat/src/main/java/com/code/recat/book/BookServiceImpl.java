package com.code.recat.book;

import com.code.recat.genre.Genre;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    public BookServiceImpl (BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }



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
                .author_id(bookRequest.getAuthor_id())
                .blurb(bookRequest.getBlurb())
                .publication_year(bookRequest.getPublication_year())
                .genres(bookRequest.getGenres())
                .isbn(bookRequest.getIsbn())
                .cover_image_url(bookRequest.getCover_image_url())
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
    public void deleteBook(Integer bookId) {
        Book book = bookRepository.findById(Long.valueOf(bookId)).orElseThrow( () -> new EntityNotFoundException("Book does not exist"));
        book.getGenres().clear();
        bookRepository.delete(book);
    }

    @Override
    public Book updateBook(Integer book_id, BookRequest bookRequest) {

        Book savedBook = bookRepository.findById(Long.valueOf(book_id)).orElseThrow(() -> new EntityNotFoundException("Book does not exist"));
        if (bookRequest.getTitle() != null){ savedBook.setTitle(bookRequest.getTitle());}
        if (bookRequest.getBlurb() != null){ savedBook.setBlurb(bookRequest.getBlurb());}
        if (bookRequest.getPublication_year() != null){ savedBook.setPublication_year(bookRequest.getPublication_year());}
        if (bookRequest.getGenres() != null){ savedBook.setGenres(bookRequest.getGenres());}
        if (bookRequest.getIsbn() != null){ savedBook.setIsbn(bookRequest.getIsbn());}
        if (bookRequest.getCover_image_url() != null){ savedBook.setCover_image_url(bookRequest.getCover_image_url());}

        return bookRepository.save(savedBook);
    }

    @Override
    public Book findBookById(Integer bookId) {
        return bookRepository.findById(Long.valueOf(bookId)).orElseThrow(() -> new EntityNotFoundException("Book does not exist"));
    }
}
