package com.socialmediaapp.service.comment;

import com.socialmediaapp.dto.comment.CommentDto;
import com.socialmediaapp.dto.comment.CommentRequestDto;
import com.socialmediaapp.dto.comment.CommentResponseDto;
import com.socialmediaapp.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    void createPostComment(CommentRequestDto commentRequestDto, Long userId, long postId);
    CommentDto readPostComment(long postId, long commentId);
    void updatePostComment(CommentRequestDto commentRequestDto, User authUser, long commentId);
    void deleteComment(long commentId, long postId, User authUser);
    List<CommentResponseDto> ListAllPostComment(User authUser, Long postId, Pageable pageable);
}
