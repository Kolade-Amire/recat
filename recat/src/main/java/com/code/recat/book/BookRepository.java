package com.code.recat.book;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByOrderByTitle(Pageable pageable);

    @Query("SELECT b FROM Book b JOIN Author a ON b.author_id = a.author_id WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR LOWER(a.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
    Page<Book> searchBooksByTitleOrAuthorName(@Param("searchQuery") String searchQuery, Pageable pageable);

}