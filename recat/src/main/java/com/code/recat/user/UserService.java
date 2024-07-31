package com.code.recat.user;

import com.code.recat.dto.UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public User getUserByEmail(String email) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
        return user;
    }

    @Transactional
    public User getUserWithTokensByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserAndTokensByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User does not exist")
        );
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
