package com.code.recat.user;

import com.code.recat.book.*;
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
    public UserDTO getUserProfile(Integer id) {
        var user = getUserById(id);
        return UserMapper.mapUserToDto(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User does not exist")
        );
    }

    @Override
    @Transactional
    public User updateUserDetails(Integer id, UserRequest userRequest) {

        var user = getUserById(id);

        if(userRequest.getFirstName() != null && userRequest.getLastName() != null){
            var fullName = userRequest.getFirstName() + " " + userRequest.getLastName();
            user.setName(fullName);
        } else if (userRequest.getFirstName() != null) {
            var fullName = userRequest.getFirstName() + " " + user.getName();
        }
        if(userRequest.getUsername() != null){
            user.setUsername(userRequest.getUsername());
        }

        return saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        var existingUser = getUserById(userId);
        existingUser.getTokens().clear();
        existingUser.getFavoriteBooks().clear();
        userRepository.delete(existingUser);
    }

    @Override
    public Set<BookViewDto> getUserFavouriteBooks(Integer userId) {
        var currentUser = getUserById(userId);
        var favBooks = currentUser.getFavoriteBooks();
        return BookDtoMapper.mapBookSetToDto(favBooks);
    }

    @Override
    @Transactional
    public Set<BookViewDto> addBookAsFavourite(Integer userId, BookViewDto book) {
        var user = getUserById(userId);
        var userFavBooks = user.getFavoriteBooks();
        var newBook = bookService.findById(book.getId());
        userFavBooks.add(newBook);
        user.setFavoriteBooks(userFavBooks);
        return getUserFavouriteBooks(userId);
    }


}


