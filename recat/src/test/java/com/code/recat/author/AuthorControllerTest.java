package com.code.recat.author;

import com.code.recat.util.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "stephamire@gmail.com", roles = "USER")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthorService authorService;
    private AuthorRequest author1;
    private AuthorRequest author2;

    @BeforeEach
    void setUp() {
        author1 = AuthorRequest.builder()
                .name("Yet AnotherAuthor")
                .dateOfBirth(LocalDate.now())
                .gender("Male")
                .build();
        author2 = AuthorRequest.builder()
                .name("Author2 Name")
                .gender("Female")
                .dateOfBirth(LocalDate.now())
                .build();
    }

    @Test
    @DirtiesContext
    void shouldReturnAllAuthors() throws Exception {
        authorService.addNewAuthor(author1);
        authorService.addNewAuthor(author2);

        this.mvc.perform(get(AppConstants.BASE_URL + "/authors")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Author2 Name"))
                .andExpect(jsonPath("$.content[1].name").value("Yet AnotherAuthor"));


    }

    @Test
    @DirtiesContext
    void shouldGetAuthorById() throws Exception {
        authorService.addNewAuthor(author1);

        this.mvc.perform(get(AppConstants.BASE_URL + "/authors/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yet AnotherAuthor"));
    }

    @Test
    @DirtiesContext
    void shouldAddNewAuthor() throws Exception {
        var request = AuthorRequest.builder()
                .name("New Author")
                .dateOfBirth(LocalDate.now())
                .gender("Male")
                .build();

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String location = this.mvc.perform(post(AppConstants.BASE_URL + "/authors")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        assertNotNull(location);

        this.mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Author"));
    }

//    @Test
//    @DirtiesContext
//    void shouldGetAuthorByName() throws Exception {
//        authorService.addNewAuthor(author1);
//    }

}
