package com.code.recat.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


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
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User does not exist")
        );
    }

    @Override
    @Transactional
    public User updateName(String email, UserRequest userRequest) {
        var fullName = userRequest.getFirstName() + " " + userRequest.getLastName();
        var user = getUserByEmail(email);
        user.setName(fullName);
        return saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        var existingUser = getUserById(id);
        existingUser.getTokens().clear();
        existingUser.getFavoriteBooks().clear();
        userRepository.delete(existingUser);
    }
}
