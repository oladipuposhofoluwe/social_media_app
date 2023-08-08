package com.socialmediaapp.service.auth;


import com.socialmediaapp.dto.login.LoginResponseDto;
import com.socialmediaapp.dto.signup.SignUpDto;
import com.socialmediaapp.dto.signup.SignupResponse;

public interface AuthService {
    LoginResponseDto login(String username, String password);

    SignupResponse signUp(SignUpDto signUpDto);
}
