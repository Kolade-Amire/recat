package com.code.recat.book;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByOrderByTitle(Pageable pageable);

    @Query("SELECT b FROM Book b " +
            "JOIN FETCH Author a ON b.author.authorId = a.authorId " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchQuery, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
    Page<Book> searchBooksByTitleOrAuthorName(@Param("searchQuery") String searchQuery, Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN FETCH b.genres g " +
            "LEFT JOIN FETCH b.comments c " +
            "WHERE b.bookId = :bookId")
    @Transactional
    Optional<Book> findBookForView(@Param("bookId") Long bookId);

}
