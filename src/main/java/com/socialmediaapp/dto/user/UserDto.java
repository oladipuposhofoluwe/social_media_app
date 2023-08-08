package com.socialmediaapp.dto.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	private long userId;
	private String userName;
    private String email;

	public UserDto(long userId, String userName, String email) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
	}

}
