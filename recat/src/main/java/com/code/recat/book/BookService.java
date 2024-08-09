package com.code.recat.book;

import com.code.recat.author.Author;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface BookService {

    Page<BookViewDto> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(BookRequest bookRequest);

    Page<BookViewDto> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    BookViewDto updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBook(Integer bookId);

    Book findById(Integer id); //this returns the original book entity

    BookViewDto findBookForView(Integer id);

}
