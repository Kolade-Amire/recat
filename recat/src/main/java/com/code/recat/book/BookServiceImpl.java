package com.code.recat.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Book updateBook(Book book) {
        return bookRepository.updateBook(book);
    }
}
