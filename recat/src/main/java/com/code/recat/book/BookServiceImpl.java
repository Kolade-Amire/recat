package com.code.recat.book;

import com.code.recat.genre.Genre;
import jakarta.persistence.EntityNotFoundException;
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
    public Book addNewBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    public Page<Book> findMatchingBooksByTitleOrAuthorName(String searchQuery, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookRepository.searchBooksByTitleOrAuthorName(searchQuery, pageable);
    }

    @Override
    public Book updateBook(Integer book_id, String title, String blurb, Integer publication_year, Set<Genre> genres, String isbn, String cover_image_url) {

        Book savedBook = bookRepository.findById(Long.valueOf(book_id)).orElseThrow(() -> new EntityNotFoundException("Book does not exist"));
        if (title != null){ savedBook.setTitle(title);}
        if (blurb != null){ savedBook.setBlurb(blurb);}
        if (publication_year != null){ savedBook.setPublication_year(publication_year);}
        if (genres != null){ savedBook.setGenres(genres);}
        if (isbn!= null){ savedBook.setIsbn(isbn);}
        if (cover_image_url != null){ savedBook.setCover_image_url(cover_image_url);}

        return bookRepository.save(savedBook);
    }
}
