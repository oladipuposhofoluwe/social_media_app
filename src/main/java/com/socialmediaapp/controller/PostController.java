package com.socialmediaapp.controller;

import com.socialmediaapp.apiresponse.ApiDataResponse;
import com.socialmediaapp.dto.post.PostRequestDto;
import com.socialmediaapp.dto.post.PostResponseDto;
import com.socialmediaapp.dto.signup.SignupResponse;
import com.socialmediaapp.security.SecuredUserInfo;
import com.socialmediaapp.service.post.PostService;
import com.socialmediaapp.utils.ApiResponseUtils;
import com.socialmediaapp.utils.UserInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final UserInfoUtil userInfoUtil;

    @PostMapping
    public ResponseEntity<ApiDataResponse<SignupResponse>> createPost(@RequestBody PostRequestDto postRequestDto) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.postService.createPost(postRequestDto, userInfo.getUser());
        return ApiResponseUtils.response(HttpStatus.OK,"Post successfully created");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiDataResponse<PostResponseDto>> readPost(@PathVariable long postId) {
        this.userInfoUtil.authUserInfo();
        PostResponseDto postResponseDto = this.postService.readPost(postId);
        return ApiResponseUtils.response(HttpStatus.OK,postResponseDto,"Post  successfully created");
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<PostResponseDto>>> ListAllPost(Pageable pageable) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        List<PostResponseDto> postResponseDto = this.postService.ListAllPost(userInfo.getUser(), pageable);
        return ApiResponseUtils.response(HttpStatus.OK, postResponseDto, "Resource retrieved successfully");
    }

    @GetMapping("/postComments/{postId}")
    public ResponseEntity<ApiDataResponse<List<PostResponseDto>>> ListCommentOfAPost(@PathVariable Long postId, Pageable pageable) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        List<PostResponseDto> postResponseDto = this.postService.ListAllPostComments(userInfo.getUser(),postId, pageable);
        return ApiResponseUtils.response(HttpStatus.OK, postResponseDto, "Resource retrieved successfully");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiDataResponse<PostResponseDto>> updatePost(@RequestBody PostRequestDto postRequestDto, @PathVariable long postId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.postService.updatePost(postId, postRequestDto, userInfo.getUser());
        return ApiResponseUtils.response(HttpStatus.OK,"Post  successfully update");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiDataResponse<PostResponseDto>> deletePost(@PathVariable long postId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.postService.deletePost(userInfo.getUser(), postId);
        return ApiResponseUtils.response(HttpStatus.OK,"Post  successfully update");
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ApiDataResponse<PostResponseDto>> likePost(@PathVariable long postId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.postService.likePost(postId, userInfo.getUser());
        return ApiResponseUtils.response(HttpStatus.OK,"Post  successfully update");
    }
}
