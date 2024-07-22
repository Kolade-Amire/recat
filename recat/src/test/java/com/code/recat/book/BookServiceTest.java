package com.code.recat.book;

import org.h2.jdbc.JdbcStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    PreparedStatement statement;
    private Book book1;
    private Book book2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book1 = new Book(1, "Book One Title", 10, "Blurb for first book.", 2000, new HashSet<>(), "25362348-72", "https://coverimage1.com");
        book2 = new Book(2, "Another Book Title", 20, "Blurb for second book.", 2010, new HashSet<>(), "25485210-89", "https://coverimage2.com");


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

        var searchQuery = "book";
        var page = bookService.findMatchingBooksByTitleOrAuthorName(searchQuery, 0, 10);

        System.out.println("Page = " + page);

        assertNotNull(page);
        assertThat(page).containsExactly(book2, book1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Another Book Title");
    }

    @Test
    void shouldUpdateBookDetails(){
        bookRepository.save(book1);
        var updatedBook = new Book(1, "Modified Title", 10, "Blurb for first book.", 2000, new HashSet<>(), "25362348-72", "https://coverimage1.com");

        bookRepository.updateBook(updatedBook);


    }

}
