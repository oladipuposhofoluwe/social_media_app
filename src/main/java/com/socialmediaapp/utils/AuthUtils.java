package com.socialmediaapp.utils;

import com.socialmediaapp.dto.login.LoginResponseDto;
import com.socialmediaapp.dto.user.UserDto;
import com.socialmediaapp.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUtils {

    public static Collection<String> buildAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public static LoginResponseDto createLoginResponse(User user, String token) {
        UserDto userDto=new UserDto(user.getId(), user.getUserName(), user.getEmail());
        return new LoginResponseDto(userDto, token);
    }

    public static boolean isEmptyToken(String token) {
        return StringUtils.isEmpty(token);
    }

    private static List<GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        privileges.forEach((privilege) -> {
            authorities.add(new SimpleGrantedAuthority(privilege));
        });
        return authorities;
    }

}
