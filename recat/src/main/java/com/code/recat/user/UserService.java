package com.code.recat.user;

import org.springframework.stereotype.Service;


@Service
class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
