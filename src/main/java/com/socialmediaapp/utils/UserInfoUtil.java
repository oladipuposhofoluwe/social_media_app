package com.socialmediaapp.utils;

import com.socialmediaapp.exceptions.UnAuthorizedException;
import com.socialmediaapp.security.SecuredUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserInfoUtil {

    public UserDetails authenticatedUserInfo() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication auth = securityContext.getAuthentication();
            if (auth != null) {
                return (UserDetails) auth.getPrincipal();
            }
        }
        throw new UnAuthorizedException("Authenticated user info not found");
    }

    public SecuredUserInfo authUserInfo() {
        UserDetails userDetails = this.authenticatedUserInfo();
        if (userDetails instanceof SecuredUserInfo) {
            return (SecuredUserInfo) userDetails;
        }
        throw new UnAuthorizedException("Authenticated user info not found");
    }
}
