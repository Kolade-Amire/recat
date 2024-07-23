package com.code.recat.book;

import com.code.recat.genre.Genre;
import org.h2.jdbc.JdbcStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    DataSource dataSource;


    private Book book1;
    private Book book2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book1 = new Book(1, "Book One Title", 10, "Blurb for first book.", 2000, new HashSet<>(), "25362348-72", "https://coverimage1.com");
        book2 = new Book(2, "Another Book Title", 20, "Blurb for second book.", 2010, new HashSet<>() , "25485210-89", "https://coverimage2.com");



    }

    @Test
    void shouldReturnAllBooksSortedInOrderOfTitle() {
        bookRepository.save(book1);
        bookRepository.save(book2);

        var page = bookService.findAllBooks(0, 10);

        assertNotNull(page);
        assertEquals(2, page.getTotalElements());
        assertEquals("Another Book Title", page.getContent().get(0).getTitle());
    }

    @Test
    @DirtiesContext
    void shouldAddANewBook() {

        Book newBook = new Book(1, "New Book Title", 10, "Blurb for New book.", 2020, new HashSet<>(), "25362348-72", "https://coverimagefornewbook.com");

        var savedBook = bookService.addNewBook(newBook);

        assertNotNull(savedBook);
        assertEquals(savedBook, newBook);
        assertThat(newBook.getBook_id()).isEqualTo(savedBook.getBook_id());
        assertThat(newBook.getTitle()).isEqualTo(savedBook.getTitle());

    }

    @Test
    @DirtiesContext
    void shouldReturnMatchingBooksWhenSearchedByTitle() throws SQLException {

        bookRepository.save(book1);
        bookRepository.save(book2);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO AUTHORS (author_id, name, date_of_birth, gender) VALUES (10,'Test Author', '1970-01-01', 'Male');");
        }

        var searchQuery = "Book One Title";
        var page = bookService.findMatchingBooksByTitleOrAuthorName(searchQuery, 0, 10);

        System.out.println("Page = " + page.getContent());

        assertNotNull(page);
        assertThat(page).containsExactly(book1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Book One Title");
    }

    @Test
    @DirtiesContext
    void shouldUpdateBookDetails() throws SQLException{
        bookRepository.save(book1);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO GENRES (genre_id, name) VALUES (2,'Non-Fiction');");
        }



        var newBook = new Book(1, "Modified Title", 10, "Modified Blurb", 2000, Set.of(new Genre(2, "Non-Fiction")), "25362348-72", "https://updatedCoverUrl.com");

        var updatedBook = bookService.updateBook(newBook.getBook_id(), newBook.getTitle(), newBook.getBlurb(), newBook.getPublication_year(), newBook.getGenres(), newBook.getIsbn(), newBook.getCover_image_url());

        assertNotNull(updatedBook);
        assertEquals(newBook.getBook_id(), updatedBook.getBook_id());
        assertEquals(newBook.getTitle(), updatedBook.getTitle());
        assertEquals(newBook.getBlurb(), updatedBook.getBlurb());
        assertEquals(newBook.getGenres(), updatedBook.getGenres());
        assertEquals(newBook.getCover_image_url(), updatedBook.getCover_image_url());


    }

}
