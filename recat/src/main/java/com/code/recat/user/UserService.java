package com.code.recat.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

   public User createUser (User newUser) {

        userRepository.save(newUser);
        return newUser;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserById(int userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
