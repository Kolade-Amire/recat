package com.code.recat.user;

import com.code.recat.author.AuthorRequest;
import com.code.recat.author.AuthorService;
import com.code.recat.book.BookDtoMapper;
import com.code.recat.book.BookRequest;
import com.code.recat.book.BookService;
import com.code.recat.genre.GenreService;
import com.code.recat.util.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "stephamire@gmail.com", roles = "USER")
public class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mvc;

    private User user;

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;

    @BeforeEach
    void setup() {

        user = User.builder()
                .name("Kolade Amire")
                .username("koladeam")
                .password("password123")
                .email("koladeamire20@gmail.com")
                .gender("Male").role(Role.USER)
                .dateJoined(LocalDateTime.now())
                .favoriteBooks(new HashSet<>())
                .tokens(new ArrayList<>())
                .build();

        userService.saveUser(user);


    }

    @Test
    @DirtiesContext
    void shouldGetUserProfile() throws Exception {

        this.mvc.perform(get(AppConstants.BASE_URL + "/user/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    @DirtiesContext
    void shouldDeleteUser() throws Exception {

        this.mvc.perform(delete(AppConstants.BASE_URL + "/user/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());


        //confirm deletion by trying to get deleted user
        this.mvc.perform(get(AppConstants.BASE_URL + "/user/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void shouldAddABookAsFavorite() throws Exception {
        //Setting up an existing book
        var testGenre = genreService.addGenre("Fantasy");

        var author1 = new AuthorRequest("Author One", LocalDate.of(2024, 8, 2), "female");
        authorService.addNewAuthor(author1);

        var book1 = new BookRequest("Book One Title", author1.getName(), "Blurb for first book.", 2000, Set.of(testGenre), "25362348-72", "https://coverimage1.com");

        //add book to database
        var addedBook = bookService.addNewBook(book1);

        var bookView = bookService.findBookForView(addedBook.getBookId());


        ObjectMapper objectMapper = new ObjectMapper();

        this.mvc.perform(put(AppConstants.BASE_URL + "/user/1/favourites")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookView)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value(bookView.getTitle()));
    }

    @Test
    @DirtiesContext
    void shouldGetUserFavouriteBooks() throws Exception {

        //Setting up an existing book
        var testGenre = genreService.addGenre("Fantasy");

        var author1 = new AuthorRequest("Author One", LocalDate.of(2024, 8, 2), "female");
        authorService.addNewAuthor(author1);

        var book1 = new BookRequest("Book One Title", author1.getName(), "Blurb for first book.", 2000, Set.of(testGenre), "25362348-72", "https://coverimage1.com");

        //add book to database
        var addedBook = bookService.addNewBook(book1);

        var bookView = bookService.findBookForView(addedBook.getBookId());


        ObjectMapper objectMapper = new ObjectMapper();

        this.mvc.perform(put(AppConstants.BASE_URL + "/user/1/favourites")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookView)));


        this.mvc.perform(get(AppConstants.BASE_URL + "/user/1/favourites")
                        .with(csrf()))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value(bookView.getTitle()));

    }

    @Test
    @DirtiesContext
    void shouldUpdateUserProfile() throws Exception {
        UserRequest request = UserRequest.builder()
                .firstName("Stephen")
                .username("stephamire")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        this.mvc.perform(put(AppConstants.BASE_URL + "/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Stephen Amire"))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

}
