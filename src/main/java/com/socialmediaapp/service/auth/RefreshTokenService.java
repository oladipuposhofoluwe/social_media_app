package com.socialmediaapp.service.auth;



import com.socialmediaapp.dto.auth.RefreshTokenRequest;
import com.socialmediaapp.model.user.UserRefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    UserRefreshToken save(UserRefreshToken refreshToken);

    Optional<UserRefreshToken> findByToken(String token);

    void deleteToken(UserRefreshToken refreshToken);

    UserRefreshToken createRefreshToken(String username);

}
