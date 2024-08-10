package com.code.recat.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserServiceTest {


    @Autowiredgit add
    private UserService userService;

    private User user;


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
        var savedUser = userService.saveUser(user);


    }

    @Test
    @DirtiesContext
    void shouldCreateANewUser() {

        User newUser = User.builder()
                .name("Ope Amire")
                .username("helenope")
                .password("password123")
                .email("helenamire@gmail.com")
                .gender("female").role(Role.USER)
                .dateJoined(LocalDateTime.now())
                .favoriteBooks(new HashSet<>())
                .tokens(new ArrayList<>())
                .build();

        var createdUser = userService.saveUser(newUser);

        assertEquals(newUser, createdUser);
        assertEquals(newUser.getUserId(), createdUser.getUserId());
        assertEquals(newUser.getEmail(), createdUser.getEmail());

    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserByEmail() {

        String email = user.getEmail();
        var result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserProfile() {

        var id = user.getUserId();
        var result = userService.getUserProfile(id);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserById() {

        Integer id = user.getUserId();
        var result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    @DirtiesContext
    void shouldUpdateUserDetails() {

        var updateRequest = UserRequest.builder()
                .firstName("Stephen")
                .lastName("Amire")
                .username("stephamire")
                .build();
        var newName = updateRequest.getFirstName() + " " + updateRequest.getLastName();

        var updatedUser = userService.updateUserDetails(user.getUserId(), updateRequest);

        assertNotNull(updatedUser);
        assertEquals(user.getUserId(), updatedUser.getUserId());
        assertNotEquals(user.getName(), updatedUser.getName());
        assertEquals(updateRequest.getUsername(), updatedUser.getProfileName());
        assertEquals(newName, updatedUser.getName());


    }


}
