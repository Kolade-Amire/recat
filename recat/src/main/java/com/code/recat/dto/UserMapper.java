//package com.code.recat.dto;
//
//import com.code.recat.user.User;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserMapper {
//
//    public static UserDTO mapUserToUserDto(User user){
//
//        List<TokenDTO> tokenDTOs = user.getTokens().stream()
//                .map(token -> new TokenDTO(token.getId(), token.getToken(), token.getTokenType(), token.isRevoked(), token.isExpired()))
//                .collect(Collectors.toList());
//
//        return new UserDTO(
//                user.getUserId(),
//                user.getName(),
//                user.getEmail(),
//                user.getGender(),
//                user.getRole(),
//                user.getDateJoined(),
//                user.isActive(),
//                user.isLocked(),
//                tokenDTOs
//        );
//    }
//}
//
