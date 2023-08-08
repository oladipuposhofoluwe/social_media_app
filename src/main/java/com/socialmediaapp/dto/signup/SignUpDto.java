package com.socialmediaapp.dto.signup;

import com.socialmediaapp.constant.SchemaConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpDto {
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;
    private String profilePicture;
}
