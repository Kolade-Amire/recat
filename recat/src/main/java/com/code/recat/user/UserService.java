package com.code.recat.user;

import com.code.recat.book.Book;
import com.code.recat.book.BookDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public interface UserService {
    User getUserByEmail(String email) throws UsernameNotFoundException;
    User getUserWithTokensByEmail(String email) throws UsernameNotFoundException;

    User saveUser(User user);

    User getUserById (Long id);

    User updateName (Long id, UserRequest userRequest);

    void deleteUser (Long id);

    Set<BookDto> getUserFavouriteBooks(Long userId);

    Set<BookDto> addBookAsFavourite(Long userId, BookDto book);

    UserDTO getUserProfile (Long id);


}
