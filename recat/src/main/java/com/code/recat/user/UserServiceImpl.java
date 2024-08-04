package com.code.recat.user;

import com.code.recat.book.Book;
import com.code.recat.book.BookDto;
import com.code.recat.book.BookDtoMapper;
import com.code.recat.book.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookService bookService;


    @Transactional
    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
    }

    @Transactional
    @Override
    public User getUserWithTokensByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserAndTokensByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
    }

   @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public UserDTO getUserProfile(Long id) {
        var user = getUserById(id);
        return UserMapper.mapUserToDto(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User does not exist")
        );
    }

    @Override
    @Transactional
    public User updateName(Long id, UserRequest userRequest) {
        var fullName = userRequest.getFirstName() + " " + userRequest.getLastName();
        var user = getUserById(id);
        user.setName(fullName);
        return saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        var existingUser = getUserById(userId);
        existingUser.getTokens().clear();
        existingUser.getFavoriteBooks().clear();
        userRepository.delete(existingUser);
    }

    @Override
    public Set<BookDto> getUserFavouriteBooks(Long userId) {
        var currentUser = getUserById(userId);
        var favBooks = currentUser.getFavoriteBooks();
        return BookDtoMapper.mapBookSetToDto(favBooks);
    }

    @Override
    @Transactional
    public Set<BookDto> addBookAsFavourite(Long userId, BookDto book) {
        var user = getUserById(userId);
        var userFavBooks = user.getFavoriteBooks();
        var newBook = bookService.findById(book.getBookId());
        userFavBooks.add(newBook);
        user.setFavoriteBooks(userFavBooks);
        return getUserFavouriteBooks(userId);
    }
}


