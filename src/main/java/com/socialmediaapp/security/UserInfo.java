package com.socialmediaapp.security;

import com.socialmediaapp.model.User;

import java.util.Collections;

public class UserInfo extends SecuredUserInfo {
    public UserInfo(User user) {
        this.user = user;
        this.authorities = Collections.emptyList();
    }
}
