package com.code.recat.book;

import org.springframework.data.domain.Page;


public interface BookService {

    Page<Book> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(Book newBook);

    Page<Book> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    Book updateBook(Book book);
}
