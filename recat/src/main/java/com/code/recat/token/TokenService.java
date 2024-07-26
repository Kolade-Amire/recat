package com.code.recat.token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TokenService {
    private final TokenRepository tokenRepository;


    public List<Token> findAllValidTokensByUser(Long userId){
     return tokenRepository.findAllValidTokensByUser(userId);
    }

    public Optional<Token> findToken(String token){
        return tokenRepository.findByToken(token);
    }

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    public void saveAllToken(List<Token> tokens){
        tokenRepository.saveAll(tokens);
    }
}
