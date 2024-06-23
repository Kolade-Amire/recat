//package com.code.recat.user;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class UserServiceTest {
//
//    private List<User> users;
//
//    @Autowired
//    private UserService userService;
//    @MockBean
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setup (){
//         users = List.of(
//                new User(99, "Kolade Amire", "koladeam", "koladeamire20@gmail.com", "M", "1234567890Kolade@", "admin", false, false),
//                new User(2, "Rodolphe Prover", "rprover1", "rprover1@artisteer.com", "M", "gB5.5\\ZM(", "user", false, false),
//                new User(3, "Sonny Stean", "sstean2", "sstean2@berkeley.edu", "M", "sR2*+6PSw\\b", "user", false, false)
//        );
//
//      when(userRepository.findAll()).thenReturn(users);
//      when(userRepository.save(ArgumentMatchers.any(User.class)))
//                .thenReturn(users.get(0));
//      when(userRepository.findByUsername(ArgumentMatchers.any(String.class)))
//              .thenReturn(Optional.ofNullable(users.get(0)));
//      when(userRepository.findById(ArgumentMatchers.any(Integer.class)))
//              .thenReturn(Optional.ofNullable(users.get(0)));
//
//
//    }
//
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
//
//    @Test
//    void shouldReturnSavedUserDetailsByUsername(){
//        String username = "koladeam";
//        Optional<User> currentUser = userService.findUserByUsername(username);
//        assertTrue(currentUser.isPresent());
//        assertThat(currentUser.get().username()).isEqualTo(username);
//    }
//
//    @Test
//    void shouldReturnSavedUserDetailsById(){
//        int userId = 99;
//        Optional<User> currentUser = userService.findUserById(userId);
//        assertTrue(currentUser.isPresent());
//        assertThat(currentUser.get().user_id()).isEqualTo(userId);
//        System.out.println(currentUser);
//    }
//
//    @Test
//    void shouldReturnAllUsers(){
//        List<User> usersList = userService.findAllUsers();
//        assertThat(usersList).isNotNull();
//        assertThat(usersList).isEqualTo(users);
//    }
//
//
//}
