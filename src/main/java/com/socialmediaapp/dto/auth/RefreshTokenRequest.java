package com.socialmediaapp.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
    private String accessToken;
    private String userName;
}
