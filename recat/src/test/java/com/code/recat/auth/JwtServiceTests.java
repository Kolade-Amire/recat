package com.code.recat.auth;


import com.code.recat.util.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JwtServiceTests {

    @InjectMocks
    @Autowired
    private JwtService jwtService;

    private UserDetails userDetails;
    private Key signInKey;
    private String token;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        userDetails = new User(
                "opeyemiamire@gmail.com", "password123", Collections.emptyList()
        );
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET_KEY);
        signInKey = Keys.hmacShaKeyFor(keyBytes);

        token = jwtService.generateToken(userDetails);
    }

    @Test
    public void shouldGenerateValidToken() {
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void shouldTestTokenExpiration() {
        Date expiredDate = new Date(System.currentTimeMillis() - 1000); //minus 1 second
        String expiredToken = Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setExpiration(expiredDate)
                .signWith(signInKey)
                .compact();

        assertThrows(ExpiredJwtException.class, () -> jwtService.extractAllClaims(expiredToken));
    }

    @Test
    public void shouldTestForInvalidToken(){
        String invalidToken = "invalid-token";
        assertThrows(MalformedJwtException.class, () -> jwtService.extractAllClaims(invalidToken));

    }

    @Test
    public void shouldTestForValidClaims(){
        Claims claims = jwtService.extractAllClaims(token);
        assertEquals(userDetails.getUsername(), claims.getSubject());
        assertEquals(claims.getAudience(), SecurityConstants.AUDIENCE);
        assertEquals(claims.getIssuer(), SecurityConstants.JWT_ISSUER);
    }


}
