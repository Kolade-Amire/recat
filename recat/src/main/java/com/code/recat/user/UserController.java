package com.code.recat.user;

import com.code.recat.book.BookViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
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

    @PutMapping
    ResponseEntity<Set<BookViewDto>> addBookToFavourites(Integer userId, Integer bookId){
        var favBooks = userService.addBookAsFavourite(userId, bookId);
        return ResponseEntity.ok(favBooks);
    }

}
