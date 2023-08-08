package com.socialmediaapp.dto.login;

import com.socialmediaapp.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto{
    private UserDto user;
    private String token;
    private String refreshToken;

    public LoginResponseDto(UserDto userDto, String token) {
        this.user = userDto;
        this.token=token;
    }
}
