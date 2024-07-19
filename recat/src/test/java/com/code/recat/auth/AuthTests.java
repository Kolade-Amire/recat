package com.code.recat.auth;


import com.code.recat.exception.PasswordsDoNotMatchException;
import com.code.recat.user.Role;
import com.code.recat.user.User;
import com.code.recat.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AuthTests {


    @Autowired
    private UserRepository userRepository;
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

    public AuthTests() {
    }


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

        userRepository.save(existingUser);

    }

    @Test
    void shouldTestIfNewUserIsRegisteredSuccessfully() {

        AuthResponse authResponse = authService.register(registerRequest);

        assertNotNull(authResponse);
        assertEquals(authResponse.getResponse().getHttpStatusCode(), HttpStatus.CREATED.value());
        assertEquals("User registered successfully.", authResponse.getResponse().getMessage());

    }

    @Test
    void shouldTestIfPasswordsDoNotMatch() {

        var wrongRequest = new RegisterRequest("Ope", "Amire", "opeamire@gmail.com", "password123", "wrong",
                "female", "female");
        assertThrows(PasswordsDoNotMatchException.class, () -> authService.register(wrongRequest));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void shouldTestIfValidUserIsAuthenticatedSuccessfully() {


        AuthRequest request = new AuthRequest(savedUserRequest.getEmail(), savedUserRequest.getPassword());

        AuthResponse response = authService.authenticate(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getResponse().getHttpStatusCode());

    }


    @Test
    void shouldReturnNoSuchElementExceptionWhenProvidedWithWrongUserDetails() {
        AuthRequest request = new AuthRequest("john.doe@example.com", "password");
        assertThrows(NoSuchElementException.class, () -> authService.authenticate(request));

    }


}
