package com.code.recat.book;

import com.code.recat.author.Author;
import com.code.recat.genre.Genre;
import jakarta.persistence.EntityNotFoundException;
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
import java.time.LocalDate;
import java.util.HashSet;
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
    private Author author1;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author1 = new Author(10L, "Author One", LocalDate.now(), "Female", new HashSet<>());
        var author2 = new Author(20L, "Author Two", LocalDate.now(), "Male", new HashSet<>());

        book1 = new Book(1L, "Book One Title", author1, "Blurb for first book.", 2000, new HashSet<>(), "25362348-72", "https://coverimage1.com", new HashSet<>());
        book2 = new Book(2L, "Another Book Title", author2, "Blurb for second book.", 2010, new HashSet<>() , "25485210-89", "https://coverimage2.com", new HashSet<>());

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

        var newBookRequest = new BookRequest( "New Book Title", author1, "Blurb for New book.", 2020, new HashSet<>(), "25362348-72", "https://coverimagefornewbook.com");

        var savedBook = bookService.addNewBook(newBookRequest);

        assertNotNull(savedBook);

        assertEquals(newBookRequest.getIsbn(), savedBook.getIsbn());
        assertEquals(newBookRequest.getTitle(), savedBook.getTitle());
        System.out.println("book created with id: " + savedBook.getBookId());

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
    void shouldUpdateExistingBookDetailsWithNewDetails() throws SQLException{
        bookRepository.save(book1);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO GENRES (genre_id, name) VALUES (2,'Non-Fiction');");
        }



        var newBookRequest = new BookRequest("Modified Title", book1.getAuthor(),"Modified Blurb", book1.getPublicationYear(), Set.of(new Genre(2L, "Non-Fiction")), "25362348-72", "https://updatedCoverUrl.com");

        var updatedBook = bookService.updateBook(book1.getBookId(), newBookRequest);

        assertNotNull(updatedBook);
        assertEquals(newBookRequest.getTitle(), updatedBook.getTitle());
        assertEquals(newBookRequest.getBlurb(), updatedBook.getBlurb());
        assertEquals(newBookRequest.getGenres(), updatedBook.getGenres());
        assertEquals(newBookRequest.getCover_image_url(), updatedBook.getCoverImageUrl());


    }

    @Test
    @DirtiesContext
    void shouldDeleteExistingBookUsingBookId(){
        bookRepository.save(book1);
        var bookId = book1.getBookId();

        bookService.deleteBook(bookId);
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(bookId));
        assertThat(bookRepository.findById(bookId)).isNotPresent();

    }

    @Test
    void shouldReturnEntityNotFoundForBookThatDoesNotExist(){
        Long bookId = 14L;
        assertThrows(EntityNotFoundException.class, () -> bookService.findBookById(bookId));
    }

}
