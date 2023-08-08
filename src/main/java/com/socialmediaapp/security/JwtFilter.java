package com.socialmediaapp.security;


import com.socialmediaapp.service.auth.TokenBlacklistService;
import com.socialmediaapp.utils.ApiResponseUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.socialmediaapp.utils.CommonUtils.castToNonNull;


@RequiredArgsConstructor
@Service
public class JwtFilter extends OncePerRequestFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;
    private final  UserDetailsService userDetailsServiceStrategies;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = jwtUtil.extractTokenFromRequest(request);
            if (token != null && !token.isBlank() && !tokenBlacklistService.isBlacklisted(castToNonNull(token)) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String userName = jwtUtil.extractUsername(token);
                UserDetails userDetails = userDetailsServiceStrategies.loadUserByUsername(userName);
                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            ApiResponseUtils.writeErrorResponse("Unknown error has occurred", response, HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error("Unknown error", e);
            return;
        }
            filterChain.doFilter(request, response);
    }

}