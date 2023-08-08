package com.socialmediaapp.service.auth;


import com.socialmediaapp.dto.login.LoginResponseDto;
import com.socialmediaapp.dto.signup.SignUpDto;
import com.socialmediaapp.dto.signup.SignupResponse;
import com.socialmediaapp.exceptions.*;
import com.socialmediaapp.model.User;
import com.socialmediaapp.model.user.UserRefreshToken;
import com.socialmediaapp.repository.user.UserRepository;
import com.socialmediaapp.security.AuthTokenProvider;
import com.socialmediaapp.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service("engineerAuthService")
public class UserAuthServiceImpl implements AuthService {
    private final AuthTokenProvider tokenProvider;
    private final UserRepository repository;
    private final AuthenticationManager engineerAuthenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistService tokenBlacklistService;
    private final PasswordEncoder passwordEncoder;

    @Value("${validity-term}")
    int validityTrm;


    @Override
    public SignupResponse signUp(SignUpDto signUpDto) {
        Optional<User> optionalUser = this.repository.findByEmailOrUserName(signUpDto.getEmail(), signUpDto.getUserName());
        if (optionalUser.isPresent()){
            throw new DuplicateEntityException("Email or Username already exist");
        }
        User user = new User();
       return this.mapUserDtoToEntity(signUpDto, user);
    }

    private SignupResponse mapUserDtoToEntity(SignUpDto signUpDto, User user) {
        user.setUserName(signUpDto.getUserName());
        user.setEmail(signUpDto.getEmail());
        if (!Objects.equals(signUpDto.getPassword(), signUpDto.getConfirmPassword())){
            throw new InvalidRequestException("password not the same as confirm password", "password mismatch");
        }
        String password = passwordEncoder.encode(signUpDto.getPassword());
        user.setPassword(password);
        User registerUser = this.repository.save(user);
        return new SignupResponse(registerUser.getId(), registerUser.getUserName(), registerUser.getEmail());
    }

    @Override
    public LoginResponseDto login(String username, String password) {
        try {
            Authentication authentication = engineerAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = this.repository.findByEmail(username).orElseThrow(() -> new InvalidCredentialsException("User with supplied credential does not exist"));
            String token = tokenProvider.generateToken(authentication.getName());
            if (isEmptyToken(token)) {
                throw new ReuseableException.UnAuthorizeException("invalid username/password:");
            }
            UserRefreshToken refreshToken = this.refreshTokenService.createRefreshToken(user.getEmail());
            LoginResponseDto authResponse = this.createLoginResponse(user, token);
            authResponse.setRefreshToken(refreshToken.getToken());
            return authResponse;

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new InvalidCredentialsException("invalid username/password:");
            }  else {
                throw e;
            }
        }
    }


    private boolean isEmptyToken(String token) {
        return AuthUtils.isEmptyToken(token);
    }

    private LoginResponseDto createLoginResponse(User user, String token) {
        return AuthUtils.createLoginResponse(user, token);
    }
}