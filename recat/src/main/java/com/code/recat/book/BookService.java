package com.code.recat.book;

import org.springframework.data.domain.Page;



public interface BookService {

    Page<BookDto> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(BookRequest bookRequest);

    Page<BookDto> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    Book updateBook(Long bookId, BookRequest bookRequest);

    void deleteBook(Long bookId);

    BookDto findBookById(Long bookId);  //This method returns a book DTO

    Book findById(Long id); //this returns the original book entity
}
