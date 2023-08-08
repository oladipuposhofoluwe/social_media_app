package com.socialmediaapp.controller.auth;

import com.socialmediaapp.apiresponse.ApiDataResponse;
import com.socialmediaapp.dto.login.LoginRequest;
import com.socialmediaapp.dto.login.LoginResponseDto;
import com.socialmediaapp.dto.signup.SignUpDto;
import com.socialmediaapp.dto.signup.SignupResponse;
import com.socialmediaapp.security.AuthTokenProvider;
import com.socialmediaapp.service.auth.AuthService;
import com.socialmediaapp.utils.ApiResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "${api.basepath-user}/")
public class AuthUserController {
    private final AuthService userAuthService;
    @PostMapping("signup")
    public ResponseEntity<ApiDataResponse<SignupResponse>> signUp(@RequestBody SignUpDto signUpDto) {
        SignupResponse signupResponse =  this.userAuthService.signUp(signUpDto);
        return ApiResponseUtils.response(HttpStatus.OK, signupResponse,"Registration successfully, please proceed to login");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<LoginResponseDto>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponseDto loginResponseDto = this.userAuthService.login(loginRequest.getUserName(),loginRequest.getPassword());
        return ApiResponseUtils.response(HttpStatus.OK, loginResponseDto, "Login successfully");
    }

}
