package com.socialmediaapp.controller;

import com.socialmediaapp.apiresponse.ApiDataResponse;
import com.socialmediaapp.dto.comment.CommentDto;
import com.socialmediaapp.dto.comment.CommentRequestDto;
import com.socialmediaapp.dto.comment.CommentResponseDto;
import com.socialmediaapp.dto.post.PostResponseDto;
import com.socialmediaapp.security.SecuredUserInfo;
import com.socialmediaapp.service.comment.CommentService;
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
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserInfoUtil userInfoUtil;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiDataResponse<String>> createPostComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable long postId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.commentService.createPostComment(commentRequestDto, userInfo.getUser().getId(), postId);
        return ApiResponseUtils.response(HttpStatus.OK,"Comment successfully created");
    }

    @GetMapping("/{postId}/{commentId}")
    public ResponseEntity<ApiDataResponse<CommentDto>> readComment(@PathVariable long postId, @PathVariable long commentId) {
        CommentDto CommentDto = this.commentService.readPostComment(postId, commentId);
        return ApiResponseUtils.response(HttpStatus.OK,CommentDto, "Post  successfully created");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiDataResponse<PostResponseDto>> updatePostComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable long commentId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.commentService.updatePostComment(commentRequestDto, userInfo.getUser(), commentId);
        return ApiResponseUtils.response(HttpStatus.OK,"Commented updated successfully");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiDataResponse<List<CommentResponseDto>>> ListAllComment(@PathVariable Long postId, Pageable pageable) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        List<CommentResponseDto> CommentResponseDto = this.commentService.ListAllPostComment(userInfo.getUser(), postId, pageable);
        return ApiResponseUtils.response(HttpStatus.OK, CommentResponseDto, "Resource retrieved successfully");
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<ApiDataResponse<PostResponseDto>> deleteComment(@PathVariable long commentId, @PathVariable long postId) {
        SecuredUserInfo userInfo = this.userInfoUtil.authUserInfo();
        this.commentService.deleteComment(commentId, postId, userInfo.getUser());
        return ApiResponseUtils.response(HttpStatus.OK,"comment deleted  successfully");
    }


}
