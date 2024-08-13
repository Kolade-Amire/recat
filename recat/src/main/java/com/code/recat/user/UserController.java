package com.code.recat.user;

import com.code.recat.book.BookViewDto;
import com.code.recat.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    ResponseEntity<UserDTO> viewUserProfile(@PathVariable Integer userId) {
        var user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/favourites")
    ResponseEntity<Set<BookViewDto>> getFavouriteBooks(@PathVariable Integer userId){
        var favBooks = userService.getUserFavouriteBooks(userId);
        return ResponseEntity.ok(favBooks);
    }


    @PutMapping("/{userId}/favourites")
    ResponseEntity<Set<BookViewDto>> addBookToFavourites(@PathVariable Integer userId, @RequestBody BookViewDto book){
        var favBooks = userService.addBookAsFavourite(userId, book);
        return ResponseEntity.ok(favBooks);
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDTO> updateUserProfile(@PathVariable Integer userId, @RequestBody UserRequest request){
        userService.updateUserDetails(userId, request);
        var updatedUser = userService.getUserProfile(userId);
        return  ResponseEntity.ok(updatedUser);

    }

}
