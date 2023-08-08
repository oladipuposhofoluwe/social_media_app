package com.socialmediaapp.dto.signup;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {

	private long userId;
	private String userName;
	private String email;
}
