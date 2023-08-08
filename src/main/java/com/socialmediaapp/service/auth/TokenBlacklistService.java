package com.socialmediaapp.service.auth;

public interface TokenBlacklistService {

    void blacklistToken(String token);

    void purgeExpiredTokens();

    boolean isBlacklisted(String token);
}
