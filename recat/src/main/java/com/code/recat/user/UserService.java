package com.code.recat.user;

import com.code.recat.dto.UserDTO;
//import com.code.recat.dto.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
    }

    @Transactional
    public User getUserWithTokensByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserWithTokens(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
