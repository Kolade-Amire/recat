package com.code.recat.user;

import com.code.recat.book.BookDto;
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
    ResponseEntity<UserDTO> viewUserProfile(@PathVariable Long userId) {
        var user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/favourites")
    ResponseEntity<Set<BookViewDto>> getFavouriteBooks(@PathVariable Long userId){
        var favBooks = userService.getUserFavouriteBooks(userId);
        return ResponseEntity.ok(favBooks);
    }

    @PutMapping
    ResponseEntity<Set<BookViewDto>> addBookToFavourites(Long userId, BookDto book){
        var favBooks = userService.addBookAsFavourite(userId, book);
        return ResponseEntity.ok(favBooks);
    }

}
