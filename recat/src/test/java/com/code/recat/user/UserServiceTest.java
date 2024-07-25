package com.code.recat.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    private List<UserDto> users;

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setup (){
         users = List.of(
                 UserDto.builder().userId(1L).name("Nate Giabucci").username("ngiabucci0")
                         .email("ngiabucci0@yelp.com").gender("female").password("uP0~!$hpmIQ#~8")
                         .role(Role.USER)
                         .build(),
                 UserDto.builder().userId(2L).name("Rodolphe Prover").username("rprover1")
                         .email("rprover1@artisteer.com").gender("male").password("password(")
                         .role(Role.ADMIN)
                         .build()
        );

//      when(userRepository.findAll()).thenReturn(users);
//      when(userRepository.save(ArgumentMatchers.any(User.class)))
//                .thenReturn(users.get(0));
//      when(userRepository.findByUsername(ArgumentMatchers.any(String.class)))
//              .thenReturn(Optional.ofNullable(users.get(0)));
//      when(userRepository.findById(ArgumentMatchers.any(Integer.class)))
//              .thenReturn(Optional.ofNullable(users.get(0)));


    }

//    @Test
//    void shouldCreateANewUser(){
//
//        User newUser = new User(99, "Kolade Amire", "koladeam", "koladeamire20@gmail.com", "M", "1234567890Kolade@", "admin", false, false);
//        User createdUser = userService.createUser(newUser);
//        assertEquals(newUser, createdUser);
//        assertEquals(newUser.user_id(), createdUser.user_id());
//        assertEquals(newUser.username(), createdUser.username());
//
//    }

//    @Test
//    void shouldReturnSavedUserDetailsByUsername(){
//        String username = "koladeam";
//        Optional<User> currentUser = userService.findUserByUsername(username);
//        assertTrue(currentUser.isPresent());
//        assertThat(currentUser.get().username()).isEqualTo(username);
//    }


}
