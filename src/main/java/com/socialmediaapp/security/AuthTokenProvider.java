package com.socialmediaapp.security;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

public interface AuthTokenProvider {

    String generateToken(String userName);

    String extractTokenFromRequest(HttpServletRequest request);

    String extractUsername(String token);

    Boolean isTokenExpired(String token);

    Collection<String> extractAuthoritiesClaims(String token);
}
