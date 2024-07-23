package com.code.recat.book;

import com.code.recat.genre.Genre;
import org.springframework.data.domain.Page;

import java.util.Set;


public interface BookService {

    Page<Book> findAllBooks(int pageNumber, int pageSize);

    Book addNewBook(Book newBook);

    Page<Book> findMatchingBooksByTitleOrAuthorName(String searchQuery,int pageNumber, int pageSize);

    Book updateBook(Integer book_id, String title, String blurb, Integer publication_year, Set<Genre> genres, String isbn, String cover_image_url);
}
