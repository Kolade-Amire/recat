package com.code.recat.config;

import com.code.recat.token.TokenService;
import com.code.recat.util.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenService tokenService;


    @Transactional
    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if(authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return;
        }
        jwt = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
        var storedToken = tokenService.findToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenService.saveToken(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
