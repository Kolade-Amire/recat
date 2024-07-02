package com.code.recat.auth;

import com.code.recat.exception.PasswordsDoNotMatchException;
import com.code.recat.user.Role;
import com.code.recat.user.User;
import com.code.recat.user.UserRepository;
import com.code.recat.util.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register (RegisterRequest request) {

        var fullName = concatenateFullName(request.getFirstname(), request.getLastname());
        var password = doPasswordsMatch(request.getPassword(), request.getConfirmPassword());
        var user = User.builder()
                .name(fullName)
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .gender(request.getGender())
                .username(request.getUsername())
                .isActive(true)
                .isLocked(false)
                .build();

        var response = new HttpResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                HttpStatus.CREATED.getReasonPhrase(),
                "User registered successfully."
        );

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .response(response)
                .build();

    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        var response = new HttpResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase(),
                "User authenticated successfully."
        );
        return AuthResponse.builder()
                .token(jwtToken)
                .response(response)
                .build();
    }

    private String doPasswordsMatch(String p1 , String p2){
        if (!p1.equals(p2)){
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }
        else return p2;
    }

    private String concatenateFullName(String firstname, String lastname){
        return firstname + " " + lastname;
    }
}
