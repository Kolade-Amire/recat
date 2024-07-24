package com.code.recat.book;

import com.code.recat.genre.Genre;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface BookService {

    Page<Book> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(BookRequest bookRequest);

    Page<Book> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    Book updateBook(Integer book_id, BookRequest bookRequest);

    void deleteBook(Integer bookId);

    Book findBookById(Integer bookId);
}
