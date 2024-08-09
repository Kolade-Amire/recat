package com.code.recat.token;

import com.code.recat.token.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private Integer id;
    private String token;
    private TokenType tokenType;
    private boolean revoked;
    private boolean expired;
}
