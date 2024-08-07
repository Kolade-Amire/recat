package com.code.recat.book;

import com.code.recat.author.Author;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface BookService {

    Page<BookViewDto> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(BookRequest bookRequest);

    Page<BookViewDto> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    Book updateBook(Long bookId, BookRequest bookRequest);

    void deleteBook(Long bookId);

//    BookDto findBookById(Long bookId);  //This method returns a book DTO

    Book findById(Long id); //this returns the original book entity

    BookViewDto findBookForView(Long id);

//    Set<Book> addBookToAuthorProfile(Author author, Long bookId);
}
