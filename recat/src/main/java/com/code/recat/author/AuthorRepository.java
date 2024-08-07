package com.code.recat.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Page<Author> getAllByOrderByName(Pageable pageable);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) = LOWER(:authorName)")
    Optional<Author> findAuthorByNameIgnoreCase(String authorName);
}
