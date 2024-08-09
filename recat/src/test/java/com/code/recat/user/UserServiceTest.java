package com.code.recat.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User newUser;

    @Autowired
    private UserService userService;


    @BeforeEach
    void setup (){

         newUser = new User(99, "Kolade Amire", "koladeam", "password123", "koladeamire20@gmail.com", "Male", Role.USER, LocalDateTime.now(),new HashSet<>(), true, false, new ArrayList<>());


        List<UserTestDto> users = List.of(
                UserTestDto.builder().userId(1).name("Nate Giabucci").username("ngiabucci0")
                        .email("ngiabucci0@yelp.com").gender("female").password("uP0~!$hpmIQ#~8")
                        .role(Role.USER)
                        .build(),
                UserTestDto.builder().userId(2).name("Rodolphe Prover").username("rprover1")
                        .email("rprover1@artisteer.com").gender("male").password("password(")
                        .role(Role.ADMIN)
                        .build()
        );

    }

    @Test
    void shouldCreateANewUser(){

        User newUser = new User(99, "Kolade Amire", "koladeam", "password123", "koladeamire20@gmail.com", "Male", Role.USER, LocalDateTime.now(),new HashSet<>(), true, false, new ArrayList<>());

        User createdUser = userService.saveUser(newUser);

        assertEquals(newUser, createdUser);
        assertEquals(newUser.getUserId(), createdUser.getUserId());
        assertEquals(newUser.getEmail(), createdUser.getEmail());

    }

    @Test
    @DirtiesContext
    void shouldReturnSavedUserByEmail(){
        var savedUser = userService.saveUser(newUser);


        String email = "koladeamire20@gmail.com";
        var result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(savedUser.getEmail(), result.getEmail());
        assertEquals(savedUser.getUserId(), result.getUserId());
    }


}
