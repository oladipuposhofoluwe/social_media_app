package com.socialmediaapp.service.user;

import com.socialmediaapp.dto.user.UserDto;
import com.socialmediaapp.model.User;

public interface UserService {
    void updateUserInfo(UserDto userDto, long userId);

    boolean followUser(User authUser, long userId);

    UserDto findUserByUsername(String userName);
}
