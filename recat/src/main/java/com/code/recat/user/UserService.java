package com.code.recat.user;

import com.code.recat.book.BookDto;
import com.code.recat.book.BookViewDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public interface UserService {
    User getUserByEmail(String email) throws UsernameNotFoundException;
    User getUserWithTokensByEmail(String email) throws UsernameNotFoundException;

    User saveUser(User user);

    User getUserById (Integer id);

    User updateUserDetails(Integer id, UserRequest userRequest);

    void deleteUser (Integer id);

    Set<BookViewDto> getUserFavouriteBooks(Integer userId);

    Set<BookViewDto> addBookAsFavourite(Integer userId, BookDto book);

    UserDTO getUserProfile (Integer id);


}
