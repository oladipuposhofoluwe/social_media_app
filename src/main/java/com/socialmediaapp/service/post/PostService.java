package com.socialmediaapp.service.post;

import com.socialmediaapp.dto.post.PostRequestDto;
import com.socialmediaapp.dto.post.PostResponseDto;
import com.socialmediaapp.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void createPost(PostRequestDto postRequestDto, User authUser);
    PostResponseDto readPost(long postId);
    void updatePost(long postId, PostRequestDto postRequestDto, User authUser);
    void deletePost(User user, long postId);
    void likePost(long postId, User authUser);
    List<PostResponseDto> ListAllPost(User authUser, Pageable pageable);
    List<PostResponseDto> ListAllPostComments(User authUser,Long postId, Pageable pageable);
}
