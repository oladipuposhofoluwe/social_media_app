package com.socialmediaapp.service.auth;


import com.socialmediaapp.dto.auth.RefreshTokenRequest;
import com.socialmediaapp.exceptions.InvalidRefreshTokenException;
import com.socialmediaapp.model.user.UserRefreshToken;
import com.socialmediaapp.repository.user.UserRefreshTokenRepository;
import com.socialmediaapp.security.AuthTokenProvider;
import com.socialmediaapp.security.UserDetailsService;
import com.socialmediaapp.utils.CodeGeneratorUtils;
import com.socialmediaapp.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;

    private final UserDetailsService userDetailsService;

    private void validateToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<UserRefreshToken> optional = this.refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken());

        if (optional.isEmpty()) {
            throw new InvalidRefreshTokenException("Token does not exist");
        }

        if (expiredToken(optional.get())) {
            throw new InvalidRefreshTokenException("Token has expired");
        }

        if (invalidTokenIssued(optional.get(), refreshTokenRequest.getUserName())) {
            throw new InvalidRefreshTokenException("Invalid Token issued");
        }
    }

    private boolean invalidTokenIssued(UserRefreshToken refreshToken, String requestUserName) {
        if (!StringUtils.equals(refreshToken.getUserName(), requestUserName)) {
            return true;
        }
        return false;
    }

    @Override
    public UserRefreshToken save(UserRefreshToken refreshToken) {
        return this.refreshTokenRepository.save((UserRefreshToken) refreshToken);
    }

    @Override
    public Optional<UserRefreshToken> findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteToken(UserRefreshToken refreshToken) {
        this.refreshTokenRepository.delete((UserRefreshToken) refreshToken);
    }

    @Override
    public UserRefreshToken createRefreshToken(String userName) {

        UserRefreshToken refreshToken =this.createRefreshTokenModel(userName);
        this.save(refreshToken);
        return refreshToken;
    }

    private UserRefreshToken createRefreshTokenModel(String userName) {
        UserRefreshToken refreshToken = new UserRefreshToken();
        refreshToken.setUserName(userName);
        int validityTrm = 10080;//mins(7days) todo:should come from configuration
        refreshToken.calculateExpiryDate(String.valueOf(validityTrm));
        refreshToken.setToken(CodeGeneratorUtils.generateRefreshToken());
        return refreshToken;
    }


    private boolean expiredToken(UserRefreshToken customerRefreshToken) {
        Date now = DateUtil.now();
        if (customerRefreshToken.getValidityTrm()!=null&&customerRefreshToken.getValidityTrm().before(now)) {
            return true;
        }
        return false;
    }
}
