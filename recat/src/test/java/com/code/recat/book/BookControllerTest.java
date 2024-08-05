package com.code.recat.book;

import com.code.recat.author.Author;
import com.code.recat.author.AuthorDto;
import com.code.recat.author.AuthorRequest;
import com.code.recat.author.AuthorService;
import com.code.recat.genre.Genre;
import com.code.recat.genre.GenreService;
import com.code.recat.util.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "stephamire@gmail.com", roles = "USER")
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private MockMvc mvc;
    private BookRequest book1;

    @BeforeEach
    void setUp() {
        Genre testGenre = genreService.addGenre("Fantasy");

        Author author1 = authorService.addNewAuthor(new AuthorRequest("Author One", LocalDate.of(2024, 8, 2), "female"));


        var author2 = authorService.addNewAuthor(new AuthorRequest("Author Two", LocalDate.of(2024, 8, 3), "male"));

        book1 = new BookRequest("Book One Title", author1, "Blurb for first book.", 2000, Set.of(testGenre), "25362348-72", "https://coverimage1.com");

        BookRequest book2 = new BookRequest("Another Book Title", author2, "Blurb for second book.", 2010, Set.of(testGenre), "25485210-89", "https://coverimage2.com");

    }

    @Test
    void shouldReturnASavedBookWhenRequestedById() throws Exception {
        bookService.addNewBook(book1);

        this.mvc.perform(get(AppConstants.BASE_URL + "/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.title").value("Book One Title"))

        ;
    }

    @Test
    @DirtiesContext
    void shouldCreateAndSaveANewBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        String location = this.mvc.perform(post(AppConstants.BASE_URL + "/books")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        assertNotNull(location);

        this.mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book One Title"))
                .andExpect(jsonPath("$.isbn").value("25362348-72"));
    }

}
