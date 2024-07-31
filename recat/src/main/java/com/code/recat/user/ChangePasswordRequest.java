package com.code.recat.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
