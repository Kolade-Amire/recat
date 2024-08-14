package com.code.recat.author;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = Author.builder()
                .name("Yet AnotherAuthor")
                .dateOfBirth(LocalDate.now())
                .gender("Male")
                .build();
        author2 = Author.builder()
                .name("Author2 Name")
                .gender("Female")
                .dateOfBirth(LocalDate.now())
                .build();

    }

    @Test
    @DirtiesContext
    void shouldGetAllAuthors() {
        authorService.saveAuthor(author1);
        authorService.saveAuthor(author2);

        var authors = authorService.findAllAuthors(0, 10);

        assertNotNull(authors);
        assertEquals(2, authors.getTotalElements());
        assertEquals("Yet AnotherAuthor", authors.getContent().get(1).getName());

    }

    @Test
    @DirtiesContext
    void shouldGetAuthorById() {
        authorService.saveAuthor(author1);

        var author = authorService.getAuthorById(1);

        assertNotNull(author);
        assertEquals(1, author.getAuthorId());
        assertEquals("Yet AnotherAuthor", author.getName());
        assertEquals("Male", author.getGender());
    }

    @Test
    @DirtiesContext
    void shouldGetAuthorByName() {
        authorService.saveAuthor(author1);

        var author = authorService.getAuthorByName("Yet AnotherAuthor");

        assertNotNull(author);
        assertEquals(1, author.getAuthorId());
        assertEquals("Yet AnotherAuthor", author.getName());
        assertEquals("Male", author.getGender());
    }


    @Test
    @DirtiesContext
    void shouldAddNewAuthor() {
        var request = AuthorRequest.builder()
                .name("New Author")
                .gender("Male")
                .dateOfBirth(LocalDate.now())
                .build();

        var savedAuthor = authorService.addNewAuthor(request);
        assertNotNull(savedAuthor);
        assertEquals(request.getName(), savedAuthor.getName());


    }

    @Test
    @DirtiesContext
    void shouldUpdateAuthor() {
        authorService.saveAuthor(author1);

        var existingAuthor = authorService.getAuthorById(1);
        var request = AuthorRequest.builder()
                .name("Updated Name")
                .build();

        authorService.updateAuthor(1, request);

        var updatedAuthor = authorService.getAuthorById(1);

        assertNotNull(updatedAuthor);
        assertEquals("Updated Name", updatedAuthor.getName());
        assertNotEquals(existingAuthor.getName(), updatedAuthor.getName());
    }

    @Test
    @DirtiesContext
    void shouldDeleteAuthor() {
        authorService.saveAuthor(author1);

        authorService.deleteAuthor(1);

        assertThrows(EntityNotFoundException.class, () -> authorService.getAuthorById(1));
    }

    @Test
    @DirtiesContext
    void shouldReturnAuthorsWithNamesMatchingSearchQuery() {
        //make sure authors exist in the test database
        authorService.saveAuthor(author1);
        authorService.saveAuthor(author2);


        var searchQuery = "Author";

        var page = authorService.searchAuthorsByName(0, 10, searchQuery);

        assertNotNull(page);
        assertEquals(2, page.getTotalElements());
        assertEquals(author2.getName(), page.getContent().get(0).getName());
    }
}
