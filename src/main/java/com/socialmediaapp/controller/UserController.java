package com.socialmediaapp.controller;


import com.socialmediaapp.apiresponse.ApiDataResponse;
import com.socialmediaapp.dto.user.UserDto;
import com.socialmediaapp.security.SecuredUserInfo;
import com.socialmediaapp.service.user.UserService;
import com.socialmediaapp.utils.ApiResponseUtils;
import com.socialmediaapp.utils.UserInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserInfoUtil userInfoUtil;

    @GetMapping("/{userName}")
    public ResponseEntity<ApiDataResponse<UserDto>> findUserByUsername(@PathVariable String userName) {
        UserDto userDto = this.userService.findUserByUsername(userName);
        return ApiResponseUtils.response(HttpStatus.OK,userDto, "Resources  retrieved successfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiDataResponse<UserDto>> updateUserInfo(@RequestBody UserDto userDto,  @PathVariable long userId) {
         this.userService.updateUserInfo(userDto, userId);
        return ApiResponseUtils.response(HttpStatus.OK,userDto, "Resources  updated successfully");
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiDataResponse<String>> followUser(@PathVariable long userId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        boolean followed = this.userService.followUser(userInfo.getUser(), userId);
        String message = followed ? "User followed successfully":"User unfollowed successfully";
        return ApiResponseUtils.response(HttpStatus.OK, message);
    }

}
