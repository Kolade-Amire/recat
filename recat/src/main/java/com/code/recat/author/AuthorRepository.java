package com.code.recat.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Page<Author> getAllByOrderByNameAsc(Pageable pageable);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%')) ORDER BY a.name")
    Page<Author> searchAuthorsByName(Pageable pageable, String authorName); //returns all authors with names that contain the search string

    Optional<Author> getAuthorByNameIgnoreCase(String name);



}
