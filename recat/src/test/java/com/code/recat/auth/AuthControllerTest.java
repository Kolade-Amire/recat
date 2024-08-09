package com.code.recat.auth;

import com.code.recat.util.AppConstants;
import com.code.recat.util.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;


    private final ObjectMapper objectMapper = new ObjectMapper();

    private RegisterRequest registerRequest;


    @BeforeEach
    void setUp () {
         registerRequest = RegisterRequest.builder()
                .firstname("Kolade")
                .lastname("Amire")
                .email("stephamire@gmail.com")
                .password("password123")
                .confirmPassword("password123")
                .username("koladeam")
                .gender("male").build();
    }

    @Test
    @DirtiesContext
    void shouldRegisterNewUser() throws Exception {

        this.mvc.perform(post(AppConstants.BASE_URL + "/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.message").value(SecurityConstants.REGISTERED_MESSAGE));


    }

    @Test
    @DirtiesContext
    void shouldAuthenticateExistingUser() throws Exception {

        this.mvc.perform(post(AppConstants.BASE_URL + "/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))
        );


        var authRequest = new AuthRequest("stephamire@gmail.com", "password123");

        this.mvc.perform(post(AppConstants.BASE_URL + "/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.message").value(SecurityConstants.AUTHENTICATED_MESSAGE))

        ;

    }

    void shouldRefreshToken() throws Exception {

    }



}
