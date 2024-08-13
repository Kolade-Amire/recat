package com.code.recat.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

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
    void shouldGetAllAuthors(){
        authorRepository.save(author1);
        authorRepository.save(author2);

        var authors = authorService.findAllAuthors(0, 10);

        assertNotNull(authors);
        assertEquals(2, authors.getTotalElements());
        assertEquals("Yet AnotherAuthor", authors.getContent().get(1).getName());

    }

    @Test
    @DirtiesContext
    void shouldGetAuthorByIdById(){
        authorRepository.save(author1);

        var author = authorService.getAuthorById(1);

        assertNotNull(author);
        assertEquals(1, author.getAuthorId());
        assertEquals("Yet AnotherAuthor", author.getName());
        assertEquals("Male", author.getGender());
    }

    @Test
    @DirtiesContext
    void shouldUpdateAuthor(){
        authorRepository.save(author1);


    }
}
