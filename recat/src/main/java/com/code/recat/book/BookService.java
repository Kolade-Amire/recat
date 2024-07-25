package com.code.recat.book;

import org.springframework.data.domain.Page;



public interface BookService {

    Page<Book> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(BookRequest bookRequest);

    Page<Book> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    Book updateBook(Long bookId, BookRequest bookRequest);

    void deleteBook(Long bookId);

    Book findBookById(Long bookId);
}
