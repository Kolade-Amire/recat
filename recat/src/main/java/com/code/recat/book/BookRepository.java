package com.code.recat.book;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface BookRepository extends ListCrudRepository<Book, Integer> {

    List<Book> findBooksByTitleContainsIgnoreCase(String searchString);

    @Query("""
            SELECT books.* FROM books
            JOIN categories  ON books.category_id = categories.category_id
            WHERE LOWER(categories.name) = LOWER(:category_name)
             """)
    List<Book> findBooksByCategoryName(@Param("category_name") String category_name);

    @Query("""
                    SELECT books.* FROM books
                    JOIN authors  ON books.author_id = authors.author_id
                     WHERE LOWER(authors.name) = LOWER(:author_name)
            """)
    List<Book> findBooksByAuthorName(@Param("author_name") String author_name);

    void editBook(@Param("book_id") String book_id);

    void deleteBookByBook_id(int book_id);



}