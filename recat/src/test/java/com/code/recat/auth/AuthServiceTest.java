package com.code.recat.auth;


import com.code.recat.exception.PasswordsDoNotMatchException;
import com.code.recat.user.Role;
import com.code.recat.user.User;
import com.code.recat.user.UserService;
import com.code.recat.util.SecurityConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {


    @Autowired
    private UserService userService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    private RegisterRequest registerRequest;
    private RegisterRequest savedUserRequest;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest("Ope", "Amire", "opeamire@gmail.com", "password123", "password123",
                "female", "female");

         savedUserRequest = new RegisterRequest("Kolade", "Amire", "stephamire@gmail.com", "password123", "password123",
                "koladeam", "male");


        User existingUser = User.builder()
                .name("Kolade Amire")
                .email("stephamire@gmail.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.USER)
                .gender("male")
                .username("koladeam")
                .dateJoined(LocalDateTime.now().minusDays(3))
                .favoriteBooks(new HashSet<>())
                .isActive(true)
                .isLocked(false)
                .build();


        userService.saveUser(existingUser);

    }

    @Test
    @DirtiesContext
    void shouldTestIfNewUserIsRegisteredSuccessfully() {

        AuthResponse authResponse = authService.register(registerRequest);

        assertNotNull(authResponse);
        assertEquals(authResponse.getResponse().getHttpStatusCode(), HttpStatus.CREATED.value());
        assertEquals(SecurityConstants.REGISTERED_MESSAGE, authResponse.getResponse().getMessage());

    }

    @Test
    @DirtiesContext
    void shouldTestIfPasswordsDoNotMatch() {

        var wrongRequest = new RegisterRequest("Ope", "Amire", "opeamire@gmail.com", "password123", "wrong",
                "female", "female");
        assertThrows(PasswordsDoNotMatchException.class, () -> authService.register(wrongRequest));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    @DirtiesContext
    void shouldTestIfValidUserIsAuthenticatedSuccessfully() {


        AuthRequest request = new AuthRequest(savedUserRequest.getEmail(), savedUserRequest.getPassword());

        AuthResponse response = authService.authenticate(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getResponse().getHttpStatusCode());

    }


    @Test
    @DirtiesContext
    void shouldReturnUsernameNotFoundExceptionWhenProvidedWithWrongUserDetails() {
        AuthRequest request = new AuthRequest("john.doe@example.com", "password");
        assertThrows(UsernameNotFoundException.class, () -> authService.authenticate(request));

    }




}
